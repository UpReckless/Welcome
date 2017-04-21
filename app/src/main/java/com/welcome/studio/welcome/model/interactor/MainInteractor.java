package com.welcome.studio.welcome.model.interactor;

import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.model.entity.Author;

import rx.Observable;
import rx.Single;


public interface MainInteractor {

    Observable<Boolean> auth();

    Observable<Boolean> uploadMainPhoto();

    Observable<Boolean> isFirstStart();

    User getUserCache();

    Observable<Boolean> checkServerConnection();

    Single<User> getUser(Author author);
}
