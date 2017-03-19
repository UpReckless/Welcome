package com.welcome.studio.welcome.model.interactor;

import android.graphics.Bitmap;
import android.location.Address;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.auth.AuthResult;
import com.welcome.studio.welcome.model.entity.AuthRequest;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.model.entity.UpdateUserRequest;
import com.welcome.studio.welcome.model.entity.UpdateUserResponse;
import com.welcome.studio.welcome.model.repository.FirebaseRepository;
import com.welcome.studio.welcome.model.repository.LocationRepository;
import com.welcome.studio.welcome.model.repository.UserRepository;

import java.io.FileNotFoundException;

import javax.inject.Inject;

import rx.Completable;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by @mistreckless on 10.02.2017. !
 */

public class MainInteractorImpl implements MainInteractor {
    private LocationRepository locationRepository;
    private UserRepository userRepository;
    private FirebaseRepository firebaseRepository;

    @Inject
    public MainInteractorImpl(UserRepository userRepository, FirebaseRepository firebaseRepository, LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.firebaseRepository = firebaseRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public Observable<Boolean> auth() {
        User user = userRepository.getUserCache();
        return locationRepository.getLastKnownLocation()
                .observeOn(Schedulers.io())
                .flatMap(locationRepository::reverseGeocodeLocation)
                .map(addresses -> generateAuthRequest(user, addresses.get(0)))
                .flatMap(userRepository::authUser)
                .flatMap(authResponse -> firebaseRepository.auth(authResponse.getToken()))
                .map(this::parseAuthResult)
                .onErrorReturn(throwable -> {
                    Log.e("AUTH ERROR", "" + throwable.getMessage(),throwable);
                    return false;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Boolean> uploadMainPhoto() {
        return Observable.just(userRepository.getUserCache().getPhotoPath())
                .observeOn(Schedulers.io())
                .flatMap(this::uploadImg)
                .flatMap(this::updateUser)
                .observeOn(AndroidSchedulers.mainThread())
                .map(this::checkUpdateUser)
                .onErrorReturn(throwable -> {
                    Log.e("ERROR UPLOAD MAIN PHOTO", throwable.getMessage());
                    return false;
                })
                .subscribeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Boolean> isFirstStart() {
        User user = userRepository.getUserCache();
        return Observable.just(user.getImei() == null && user.getNickname() == null);
    }

    @Override
    public User getUserCache() {
        return userRepository.getUserCache();
    }

    @Override
    public Observable<Uri> downloadMyMainPhotoUri() {
        User user = userRepository.getUserCache();
        return Observable.just(user.getPhotoRef())
                .flatMap(firebaseRepository::getDownloadUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable downloadMyMainPhotoBitmap(Bitmap bitmap) {
        return Completable.create(subscriber -> {
            userRepository.downloadMyMainPhotoBitmap(bitmap);
            subscriber.onCompleted();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private AuthRequest generateAuthRequest(User user, Address address) {
        return new AuthRequest(address.getLocality(), address.getCountryName(), user.getImei(), user.getId());
    }

    private boolean parseAuthResult(AuthResult authResult) {
        return true;//mock this
    }

    private boolean checkUpdateUser(UpdateUserResponse response) {
        return userRepository.getUserCache().getPhotoRef().equals(response.getPhotoRef());
    }

    private Observable<Uri> uploadImg(String photoPath) {
        try {
            return firebaseRepository.uploadImage(photoPath, userRepository.getUserCache().getId());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Observable<UpdateUserResponse> updateUser(Uri photoRef) {
        User user = userRepository.getUserCache();
        UpdateUserRequest request = new UpdateUserRequest();
        request.setId(user.getId());
        request.setImei(user.getImei());
        request.setName(user.getNickname());
        request.setEmail(user.getEmail());
        request.setPhotoRef(String.valueOf(photoRef));
        return userRepository.updateUser(request);
    }
}
