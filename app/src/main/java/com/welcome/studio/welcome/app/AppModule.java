package com.welcome.studio.welcome.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.welcome.studio.welcome.util.Constance;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Royal on 28.11.2016.
 */
@Module
public class AppModule {

    @NonNull
    private Context context;

    public AppModule(@NonNull Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(){
        return context.getSharedPreferences(Constance.SharedPreferencesHolder.KEY, Context.MODE_PRIVATE);
    }
}
