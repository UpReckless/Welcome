package com.welcome.studio.welcome.ui.profile;

import com.welcome.studio.welcome.model.interactor.ProfileInteractor;
import com.welcome.studio.welcome.model.interactor.ProfileInteractorImpl;
import com.welcome.studio.welcome.model.repository.UserRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Royal on 15.01.2017.
 */
@ProfileScope
@Module
public class ProfileModule {

    @ProfileScope
    @Provides
    public ProfileInteractor provideProfileInteractor(UserRepository userRepository){
        return new ProfileInteractorImpl(userRepository);
    }

}
