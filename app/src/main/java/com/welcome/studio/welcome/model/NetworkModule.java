package com.welcome.studio.welcome.model;

import android.support.annotation.NonNull;

import com.welcome.studio.welcome.model.repository.ServerRepositoryCreator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Royal on 28.11.2016.
 */
@Module
public class NetworkModule {

    @Provides
    @NonNull
    @Singleton
    ModelFirebase provideFirebaseStorage(){
        return new ModelFirebase();
    }

    @Provides
    @Singleton
    ModelServer provideModelServer(){
        return new ModelServerImpl(ServerRepositoryCreator.getUserRepository());
    }

}
