package com.welcome.studio.welcome.model.interactor;

import android.graphics.Bitmap;
import android.net.Uri;

import com.welcome.studio.welcome.model.data.User;

import rx.Completable;
import rx.Observable;


public interface MainInteractor {

    Observable<Boolean> auth();

    Observable<Boolean> uploadMainPhoto();

    Observable<Boolean> isFirstStart();

    User getUserCache();

    Observable<Uri> downloadMyMainPhotoUri();

    Completable downloadMyMainPhotoBitmap(Bitmap bitmap);

    Observable<Boolean> checkServerConnection();
}
