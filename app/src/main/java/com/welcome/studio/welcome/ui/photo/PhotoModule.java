package com.welcome.studio.welcome.ui.photo;

import android.content.SharedPreferences;

import com.welcome.studio.welcome.model.interactor.PhotoInteractor;
import com.welcome.studio.welcome.model.interactor.PhotoInteractorImpl;
import com.welcome.studio.welcome.model.repository.FirebaseRepository;
import com.welcome.studio.welcome.model.repository.LocationRepository;
import com.welcome.studio.welcome.model.repository.PhotoRepository;
import com.welcome.studio.welcome.model.repository.PhotoRepositoryImpl;
import com.welcome.studio.welcome.model.repository.UserRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by @mistreckless on 18.01.2017. !
 */
@PhotoScope
@Module
public class PhotoModule {

    @PhotoScope
    @Provides
    public PhotoRepository providePhotoRepository(SharedPreferences spf){
        return new PhotoRepositoryImpl(spf);
    }

    @PhotoScope
    @Provides
    public PhotoInteractor providePhotoInteractor(PhotoRepository photoRepository, UserRepository userRepository,
                                                  FirebaseRepository firebaseRepository, LocationRepository locationRepository){
        return new PhotoInteractorImpl(photoRepository,userRepository,firebaseRepository,locationRepository);
    }
}
