package com.welcome.studio.welcome.ui.module.profile;

import com.welcome.studio.welcome.app.RxBus;
import com.welcome.studio.welcome.model.interactor.PostInteractor;
import com.welcome.studio.welcome.model.interactor.ProfileInteractor;
import com.welcome.studio.welcome.model.interactor.impl.ProfileInteractorImpl;
import com.welcome.studio.welcome.model.repository.FirebaseRepository;
import com.welcome.studio.welcome.model.repository.PostRepository;
import com.welcome.studio.welcome.model.repository.UserRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by @mistreckless on 15.01.2017. !
 */
@ProfileScope
@Module
public class ProfileModule {

    @ProfileScope
    @Provides
    public ProfileInteractor provideProfileInteractor(UserRepository userRepository, PostRepository postRepository, FirebaseRepository firebaseRepository, RxBus rxBus, PostInteractor postInteractor){
        return new ProfileInteractorImpl(userRepository, postRepository, firebaseRepository, rxBus, postInteractor);
    }

}