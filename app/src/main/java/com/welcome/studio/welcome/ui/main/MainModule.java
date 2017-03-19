package com.welcome.studio.welcome.ui.main;

import android.support.annotation.NonNull;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.welcome.studio.welcome.model.interactor.MainInteractor;
import com.welcome.studio.welcome.model.interactor.MainInteractorImpl;
import com.welcome.studio.welcome.model.repository.FirebaseRepository;
import com.welcome.studio.welcome.model.repository.LocationRepository;
import com.welcome.studio.welcome.model.repository.UserRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by @mistreckless on 30.11.2016. !
 */
@Module
@MainScope
public class MainModule {
    @NonNull
    private View view;
    public MainModule(@NonNull View view){
        this.view=view;
    }
    @Provides
    @MainScope
    public View provideMainActivity(){return view;}

    @MainScope
    @Provides
    public RxPermissions provideRxPermissions(View activity){return new RxPermissions((MainActivity)activity);}

    @MainScope
    @Provides
    public MainInteractor provideMainInteractor(UserRepository userRepository, FirebaseRepository firebaseRepository, LocationRepository locationRepository){
        return new MainInteractorImpl(userRepository, firebaseRepository, locationRepository);
    }

}
