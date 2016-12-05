package com.welcome.studio.welcome.presenter.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.model.ModelFirebase;
import com.welcome.studio.welcome.model.ModelServer;
import com.welcome.studio.welcome.model.entity.DaoSession;
import com.welcome.studio.welcome.model.entity.ExceptionJSONInfo;
import com.welcome.studio.welcome.model.entity.RaitingDao;
import com.welcome.studio.welcome.model.entity.User;
import com.welcome.studio.welcome.model.entity.UserDao;
import com.welcome.studio.welcome.presenter.LastPagePresenter;
import com.welcome.studio.welcome.util.Constance;
import com.welcome.studio.welcome.util.ExceptionUtil;
import com.welcome.studio.welcome.view.fragment.LastPageFragment;

import java.io.FileNotFoundException;
import java.net.SocketTimeoutException;

import javax.inject.Inject;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;

/**
 * Created by Royal on 20.10.2016.
 */
public class LastPagePresenterImpl implements LastPagePresenter {

    @Inject
    ModelServer modelServer;
    @Inject
    ModelFirebase modelFirebase;
    @Inject
    DaoSession daoSession;
    private LastPageFragment view;
    private boolean photoExists = false;
    private String pathToPhoto;

    @Inject
    public LastPagePresenterImpl(LastPageFragment view) {
        this.view = view;
        view.getComponent().inject(this);
    }

    private void registry(User user) {
        Log.e("model",String.valueOf(modelServer!=null));
        modelServer.regUser(user).subscribe(new Observer<User>() {
            @Override
            public void onCompleted() {
                Log.e("Presenter: ", "On Completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("On error ", e.toString());
                if (e instanceof HttpException) {
                    ExceptionJSONInfo exceptionJSONInfo = ExceptionUtil.parseEx(((HttpException) e).response());
                    view.showToast(exceptionJSONInfo.getMessage());
                }
                if (e instanceof SocketTimeoutException) {
                    view.showToast(R.string.toast_error_connect);
                }

            }

            @Override
            public void onNext(User user) {
                modelServer.authUser(user.getImei()).subscribe(token -> {
                    UserDao userDao = daoSession.getUserDao();
                    RaitingDao raitingDao = daoSession.getRaitingDao();
                    if (photoExists) {
                        modelFirebase.auth(token)
                                .addOnFailureListener(failrule -> Log.e("auth fail", failrule.getMessage()))
                                .addOnCompleteListener(task -> {
                                    try {
                                        modelFirebase
                                                .uploadImage(pathToPhoto, user.getId())
                                                .addOnFailureListener(failure -> Log.e("Fail to upload ", failure.getMessage()))
                                                .addOnSuccessListener(success -> {
                                                    Log.e("Success upload ", success.getDownloadUrl() + "");
                                                    user.setPhotoRef(String.valueOf(success.getDownloadUrl()));
                                                    modelServer.updateUser(user).subscribe(user1 -> {
                                                        Log.e("is update", " successfully");
                                                        userDao.insert(user1);
                                                        raitingDao.insert(user1.getRaitingFromJson());
                                                    });
                                                });
                                        view.savePreferences(user.getImei(), user.getNickname());
                                        view.start(true);
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                });
                    } else {
                        userDao.insert(user);
                        raitingDao.insert(user.getRaitingFromJson());
                        view.savePreferences(user.getImei(), user.getNickname());
                        view.start(false);
                    }
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
                    photoExists = true;
                    pathToPhoto = cursor.getString(columnIndex);
                    view.drawPhoto(pathToPhoto);
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
