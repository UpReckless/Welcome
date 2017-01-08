package com.welcome.studio.welcome.util;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Royal on 29.11.2016.
 */
@Singleton
@Module
public class UtilsModule {
    private Context context;
    public UtilsModule(Context context){
        this.context=context;
    }

    @Singleton
    @Provides
    public LocationService provideLocationService(Context context){
        return new LocationService(context);
    }
}
