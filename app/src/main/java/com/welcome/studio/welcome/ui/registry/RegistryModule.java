package com.welcome.studio.welcome.ui.registry;

import com.welcome.studio.welcome.model.interactor.RegistryInteractor;
import com.welcome.studio.welcome.model.interactor.RegistryInteractorImpl;
import com.welcome.studio.welcome.model.repository.LocationRepository;
import com.welcome.studio.welcome.model.repository.UserRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by @mistreckless on 06.01.2017. !
 */
@RegistryScope
@Module
public class RegistryModule {

    @RegistryScope
    @Provides
    public RegistryInteractor provideRegistryInteractor(UserRepository userRepository, LocationRepository locationRepository){
        return new RegistryInteractorImpl(userRepository,locationRepository);
    }
}
