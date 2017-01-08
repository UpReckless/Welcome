package com.welcome.studio.welcome.ui.registry;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.model.ModelFirebase;
import com.welcome.studio.welcome.model.ModelServer;
import com.welcome.studio.welcome.model.pojo.AuthRequest;
import com.welcome.studio.welcome.model.pojo.AuthResponse;
import com.welcome.studio.welcome.model.pojo.ExceptionJSONInfo;
import com.welcome.studio.welcome.model.pojo.User;
import com.welcome.studio.welcome.util.Constance;
import com.welcome.studio.welcome.util.ExceptionUtil;
import com.welcome.studio.welcome.util.LocationService;

import java.io.FileNotFoundException;
import java.net.SocketTimeoutException;

import javax.inject.Inject;

import retrofit2.adapter.rxjava.HttpException;

import static com.welcome.studio.welcome.util.Constance.CallbackPermissionsHolder.REQUEST_READ_PHONE_STATE;
import static com.welcome.studio.welcome.util.Constance.CallbackPermissionsHolder.REQUEST_WRITE_EXTERNAL_STORAGE;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.CITY;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.ID;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.IMEI;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.LAT;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.LON;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.NAME;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.PHOTO_PATH;

/**
 * Created by Royal on 06.01.2017.
 */

public class SignUpPresenterImpl implements SignUpPresenter {
    private static final String TAG = "SignUpPresenter";

    private SignUpView view;
    private String pathToPhoto;
    @Inject
    ModelServer modelServer;
    @Inject
    ModelFirebase modelFirebase;
    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    LocationService locationService;

    @Inject
    public SignUpPresenterImpl(SignUpView view) {
        this.view = view;
        view.getComponent().inject(this);
    }

    @Override
    public void onBtnGoClick() {
        if (checkParams(view.getEdName(), view.getEdEmail()))
            if (view.permissionCheck(Manifest.permission.READ_PHONE_STATE)) {
                try {
                    Location location = locationService.getLocation();
                    registry(view.getImei(), view.getEdName(), view.getEdEmail(), location.getLatitude(), location.getLongitude());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else
                view.requestPermission(new String[]{Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_READ_PHONE_STATE);
    }

    private void registry(@NonNull String imei, @NonNull String name, @Nullable String email, double lat, double lon) {
        User newUser = new User(name, imei, email, lat, lon);
        modelServer.regUser(newUser).subscribe(user -> {
            modelServer.authUser(new AuthRequest(lat, lon, imei)).subscribe(authResponse -> {
                modelFirebase.auth(authResponse.getToken())
                        .addOnFailureListener(fail -> Log.e(TAG, fail.getMessage()))
                        .addOnCompleteListener(task -> {
                            if (pathToPhoto != null)
                                try {
                                    modelFirebase
                                            .uploadImage(pathToPhoto, user.getId())
                                            .addOnFailureListener(fail -> Log.e(TAG, fail.getMessage()))
                                            .addOnSuccessListener(taskSnapshot -> {
                                                Log.e(TAG, taskSnapshot.toString());
                                                user.setPhotoRef(String.valueOf(taskSnapshot.getDownloadUrl()));
                                                user.setImei(imei);
                                                modelServer.updateUser(user).subscribe(user1 -> Log.e(TAG, "successfully updated")
                                                        , e -> Log.e(TAG, e.getMessage()));
                                            });
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            saveSharedPreferences(user, imei, authResponse);
                            view.start();
                        });
            }, e -> Log.e(TAG, e.toString()));
        }, e -> {
            Log.e(TAG, e.getMessage());
            if (e instanceof HttpException) {
                ExceptionJSONInfo exceptionJSONInfo = ExceptionUtil.parseEx(((HttpException) e).response());
                view.showToast(exceptionJSONInfo.getMessage());
            }
            if (e instanceof SocketTimeoutException) {
                view.showToast(R.string.toast_error_connect);
            }
        });
    }

    private void saveSharedPreferences(User user, String imei, AuthResponse authResponse) {
        sharedPreferences.edit()
                .putString(NAME, user.getNickname())
                .putLong(ID, user.getId())
                .putString(IMEI, imei)
                .putString(PHOTO_PATH, pathToPhoto)
                .putLong(LAT, Double.doubleToLongBits(user.getLatitude()))
                .putLong(LON, Double.doubleToLongBits(user.getLongitude()))
                .putString(CITY, authResponse.getCity())
                .apply();
    }

    private boolean checkParams(String name, String email) {
        if (!name.isEmpty()) {
            if (email.contains("@") || email.isEmpty()) {
                return true;
            } else view.showToast(R.string.toast_error_email);
        } else view.showToast(R.string.toast_error_name);
        return false;
    }

    @Override
    public void onImgPhotoTouch() {
        if (view.permissionCheck(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            view.sendIntentToGallery();
        else
            view.requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_WRITE_EXTERNAL_STORAGE: {
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED))
                    view.sendIntentToGallery();
                break;
            }
            case REQUEST_READ_PHONE_STATE: {
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    try {
                        Location location = locationService.getLocation();
                        registry(view.getImei(), view.getEdName(), view.getEdEmail(), location.getLatitude(), location.getLongitude());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constance.IntentCodeHolder.LOAD_PHOTO_FROM_GALLERY &&
                resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            try (Cursor cursor = view.getContentResolver().query(selectedImage, filePathColumn, null, null, null)) {
                if (cursor != null) {
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    pathToPhoto = cursor.getString(columnIndex);
                    view.drawPhoto(pathToPhoto);
                }
            }
        }
    }
}
