package com.welcome.studio.welcome.model.interactor;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.welcome.studio.welcome.model.data.User;

import rx.Observable;

/**
 * Created by @mistreckless on 07.02.2017. !
 */
public interface RegistryInteractor {
    Observable<Boolean> controlNextButton(@NonNull Observable<CharSequence> nameFieldListener);

    Observable<CharSequence> controlHeaderHelloView(@NonNull Observable<CharSequence> nameFieldListener);

    Observable<User> regNewUser(@NonNull String name, String imei);

    Observable<Void> setLanguage(int index);

    User getUserCache();

    Observable<String> controlMainPhoto(@NonNull Intent data);

}
