package com.welcome.studio.welcome.model.interactor;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.model.repository.LocationRepository;
import com.welcome.studio.welcome.model.repository.UserRepository;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RegistryInteractorImpl implements RegistryInteractor {
    private UserRepository userRepository;
    private LocationRepository locationRepository;

    @Inject
    public RegistryInteractorImpl(UserRepository userRepository, LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public Observable<Boolean> controlNextButton(@NonNull Observable<CharSequence> nameFieldListener) {
        return nameFieldListener
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .flatMap(userRepository::requestDuplicate)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(throwable -> {
                    Log.e("throw", throwable.getMessage());
                    return false;
                });
    }

    @Override
    public Observable<CharSequence> controlHeaderHelloView(@NonNull Observable<CharSequence> nameFieldListener) {
        return nameFieldListener.map(name -> TextUtils.isEmpty(name) ? name : ", " + name);
    }

    @Override
    public Observable<User> regNewUser(@NonNull String name, String imei) {
        return locationRepository.getLastKnownLocation()
                .observeOn(Schedulers.io())
                .flatMap(locationRepository::reverseGeocodeLocation)
                .flatMap(addresses->userRepository.registryNewUser(name,imei,
                        addresses.get(0).getLocality(),addresses.get(0).getCountryName()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Void> setLanguage(int index) {
        return userRepository.saveLanguage(index);
    }

    @Override
    public User getUserCache() {
        return userRepository.getUserCache();
    }

    @Override
    public Observable<String> controlMainPhoto(@NonNull Intent data) {
        return userRepository.findPhoto(data)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
