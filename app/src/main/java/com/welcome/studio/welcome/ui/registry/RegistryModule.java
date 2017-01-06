package com.welcome.studio.welcome.ui.registry;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Royal on 06.01.2017.
 */
@RegistryScope
@Module
public class RegistryModule {
    @NonNull
    private SignUpView view;

    public RegistryModule(@NonNull SignUpView view){
        this.view=view;
    }
    @RegistryScope
    @Provides
    public SignUpView provideSignUpView(){return view;}

    @RegistryScope
    @Provides
    public SignUpPresenter provideSignUpPresenter(SignUpView view){return new SignUpPresenterImpl(view);}

}
