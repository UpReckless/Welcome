package com.welcome.studio.welcome.ui.main;

import com.welcome.studio.welcome.ui.profile.ProfileComponent;
import com.welcome.studio.welcome.ui.profile.ProfileModule;
import com.welcome.studio.welcome.ui.registry.RegistryComponent;
import com.welcome.studio.welcome.ui.registry.RegistryModule;

import dagger.Subcomponent;

/**
 * Created by Royal on 01.12.2016.
 */
@MainScope
@Subcomponent(modules = {MainModule.class})
public interface MainComponent {
    void inject(MainActivity mainActivity);
    void inject(PresenterImpl presenter);
    RegistryComponent plus(RegistryModule registryModule);
    ProfileComponent plus(ProfileModule profileModule);
}
