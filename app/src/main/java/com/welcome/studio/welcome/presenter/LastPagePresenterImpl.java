package com.welcome.studio.welcome.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.model.ModelFirebaseStorage;
import com.welcome.studio.welcome.model.ModelServer;
import com.welcome.studio.welcome.model.ModelServerImpl;
import com.welcome.studio.welcome.model.entity.User;
import com.welcome.studio.welcome.util.AuthService;
import com.welcome.studio.welcome.util.BitmapCreator;
import com.welcome.studio.welcome.util.Constance;
import com.welcome.studio.welcome.view.fragment.firststart.LastPageFragment;

import java.io.FileNotFoundException;

import rx.Observer;

/**
 * Created by Royal on 20.10.2016.
 */
public class LastPagePresenterImpl implements LastPagePresenter {

    private ModelServer modelServer;
    private LastPageFragment view;
    private boolean photoExists = false;
    private String pathToPhoto;

    public LastPagePresenterImpl(LastPageFragment view) {
        this.view = view;
        modelServer = new ModelServerImpl();
    }

    private void registry(User user) {
        modelServer.regUser(user).subscribe(new Observer<User>() {
            @Override
            public void onCompleted() {
                Log.e("Presenter: ", "On Completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("Presenter: ", "On Error " + e.toString());

            }

            @Override
            public void onNext(User user) {
                modelServer.authUser(user.getImei()).subscribe(token -> {
                    view.savePreferences(user.getImei(), user.getNickname());
                    if (photoExists) {
                        AuthService.auth(token)
                                .addOnFailureListener(failrule -> Log.e("auth fail", failrule.getMessage()))
                                .addOnCompleteListener(task -> {
                                    try {
                                        ModelFirebaseStorage.getInstance()
                                                .uploadImage(pathToPhoto, String.valueOf(user.getId()))
                                                .addOnFailureListener(failure -> Log.e("Fail to upload ", failure.getMessage()))
                                                .addOnSuccessListener(success -> {
                                                    Log.e("Success upload ", success.getDownloadUrl() + "");
                                                    User updateableUser=new User(user.getNickname(),user.getEmail(),user.getImei());
                                                    updateableUser.setId(user.getId());
                                                    updateableUser.setPhotoRef(String.valueOf(success.getDownloadUrl()));
                                                    modelServer.updateUser(updateableUser).subscribe(user1 -> {
                                                        Log.e("is update"," successfully");
                                                    });
                                                });
                                        //save to server and db
                                        view.start(true);
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                });
                    } else view.start(false);
                });
            }
        });
    }

    @Override
    public void onPhotoReturn(int requestCode, int resultCode, Intent data, Context context) {
        if (requestCode == Constance.IntentCodeHolder.LOAD_PHOTO_FROM_GALLERY &&
                resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    Bitmap bitmap = BitmapCreator.decodeSampledBitmapFromPath(cursor.getString(columnIndex), 100, 100);
                    photoExists = true;
                    pathToPhoto = cursor.getString(columnIndex);
                    view.drawPhoto(bitmap);
                }
            } catch (Exception e) {
                view.showToast(R.string.toast_error_load_message);
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        }
    }

    @Override
    public void regBtnClick(String name, String email, String imei) {
        if (checkParams(name, email)) {
            registry(new User(name, email, imei));
        }
    }

    private boolean checkParams(String name, String email) {
        if (!name.isEmpty()) {
            if (email.contains("@") || email.isEmpty()) {
                return true;
            } else view.showToast(R.string.toast_error_email);
        } else view.showToast(R.string.toast_error_name);
        return false;
    }
}
