package com.welcome.studio.welcome.ui.wall;

import com.welcome.studio.welcome.model.interactor.WallInteractor;
import com.welcome.studio.welcome.model.interactor.WallInteractorImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Royal on 18.01.2017.
 */
@WallScope
@Module
public class WallModule {

    @WallScope
    @Provides
    WallInteractor provideWallInteractor(){return new WallInteractorImpl();}
}
