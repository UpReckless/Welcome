package com.welcome.studio.welcome.dagger.module;

import android.support.annotation.NonNull;

import com.welcome.studio.welcome.model.ModelFirebase;
import com.welcome.studio.welcome.model.ModelServer;
import com.welcome.studio.welcome.model.ModelServerImpl;
import com.welcome.studio.welcome.model.repository.ServerRepository;
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
    ServerRepository provideUserRepository(){
        return ServerRepositoryCreator.getUserRepository();
    }

    @Provides
    @Singleton
    ModelServer provideModelServer(){
        return new ModelServerImpl();
    }

}
