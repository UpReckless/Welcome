package com.welcome.studio.welcome.ui.wall;

import com.welcome.studio.welcome.model.interactor.WallInteractor;
import com.welcome.studio.welcome.model.interactor.WallInteractorImpl;
import com.welcome.studio.welcome.model.repository.FirebaseRepository;
import com.welcome.studio.welcome.model.repository.UserRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by @mistreckless on 18.01.2017. !
 */
@WallScope
@Module
public class WallModule {

    @WallScope
    @Provides
    WallInteractor provideWallInteractor(FirebaseRepository firebaseRepository, UserRepository userRepository){
        return new WallInteractorImpl(firebaseRepository, userRepository);
    }
}
