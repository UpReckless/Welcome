package com.welcome.studio.welcome.ui.module.main;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.welcome.studio.welcome.model.interactor.MainInteractor;
import com.welcome.studio.welcome.model.interactor.impl.MainInteractorImpl;
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

    private View view;
    public MainModule(View view){
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
