package com.welcome.studio.welcome.model.interactor;

import com.welcome.studio.welcome.model.data.User;

import rx.Observable;


public interface MainInteractor {

    Observable<Boolean> auth();

    Observable<Boolean> uploadMainPhoto();

    Observable<Boolean> isFirstStart();

    User getUserCache();

    Observable<Boolean> checkServerConnection();
}
