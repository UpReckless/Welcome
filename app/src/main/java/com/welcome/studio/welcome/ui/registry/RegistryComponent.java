package com.welcome.studio.welcome.ui.registry;

import dagger.Subcomponent;

/**
 * Created by Royal on 06.01.2017.
 */
@RegistryScope
@Subcomponent(modules = {RegistryModule.class})
public interface RegistryComponent {
    void inject(SignUp signUp);
    void inject(SignUpPresenterImpl signUpPresenter);
}
