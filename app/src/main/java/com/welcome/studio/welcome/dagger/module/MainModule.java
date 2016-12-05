package com.welcome.studio.welcome.dagger.module;

import android.support.annotation.NonNull;

import com.welcome.studio.welcome.dagger.scope.MainScope;
import com.welcome.studio.welcome.presenter.MainActivityPresenter;
import com.welcome.studio.welcome.presenter.impl.MainActivityPresenterImpl;
import com.welcome.studio.welcome.view.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Royal on 30.11.2016.
 */
@Module
@MainScope
public class MainModule {
    @NonNull
    private MainActivity mainActivity;
    public MainModule(@NonNull MainActivity mainActivity){
        this.mainActivity=mainActivity;
    }
    @Provides
    @MainScope
    public MainActivity provideMainActivity(){return mainActivity;}

    @Provides
    @MainScope
    public MainActivityPresenter provideMainActivityPresenter(MainActivity mainActivity){
        return new MainActivityPresenterImpl(mainActivity);
    }
}
