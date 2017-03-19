package com.welcome.studio.welcome.model.interactor;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.upreckless.support.portraitcamerasupport.CameraSupport;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.repository.FirebaseRepository;
import com.welcome.studio.welcome.model.repository.LocationRepository;
import com.welcome.studio.welcome.model.repository.PhotoRepository;
import com.welcome.studio.welcome.model.repository.UserRepository;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Royal on 11.02.2017. !
 */

public class PhotoInteractorImpl implements PhotoInteractor {
    private static final String TAG="PhotoInteractor";
    private PhotoRepository photoRepository;
    private UserRepository userRepository;
    private FirebaseRepository firebaseRepository;
    private LocationRepository locationRepository;

    @Inject
    public PhotoInteractorImpl(PhotoRepository photoRepository,UserRepository userRepository,
                               FirebaseRepository firebaseRepository, LocationRepository locationRepository) {
        this.photoRepository = photoRepository;
        this.userRepository=userRepository;
        this.firebaseRepository=firebaseRepository;
        this.locationRepository=locationRepository;
    }

    @Override
    public Observable<String> takeTmpPicture(CameraSupport cameraSupport, byte[] bytes) {
        return Observable.just(photoRepository.saveTmpPhoto(cameraSupport, bytes))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Post> saveFinishedPicture(Bitmap outputBitmap) throws IOException {
        return locationRepository.getLastKnownLocation()
                .flatMap(locationRepository::reverseGeocodeLocation)
                .doOnNext(adresses->Log.e(TAG,adresses.get(0).getThoroughfare()))
                .zipWith(locationRepository.getLastKnownLocation(),(addresses, location) -> {
                    try {
                        return photoRepository.saveFinishedPicture(outputBitmap,addresses.get(0),
                                location,userRepository.getUserCache());
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(TAG,e.getMessage(),e);
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Boolean> sharePost(String description, String[] tags, boolean dressCode, long deleteTime) {
        Post post=photoRepository.getSharedPost(description, tags, dressCode,deleteTime);
        try{
            return firebaseRepository.uploadImage(post.getContentPath(),post.getAuthor().getuId())
                    .map(uri -> generatePostWithRef(uri,post))
                    .observeOn(Schedulers.io())
                    .flatMap(firebaseRepository::sharePost)
                    .flatMap(postReference -> firebaseRepository.setPostTags(post.getCountry(),post.getCity()
                            ,postReference.getKey(),post.getTags()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return Observable.just(false);
        }
    }

    private Post generatePostWithRef(Uri uri, Post post) {
        post.setContentRef(String.valueOf(uri));
        return post;
    }


}
