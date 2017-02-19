package com.welcome.studio.welcome.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.snappydb.DB;
import com.welcome.studio.welcome.util.Constance;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Royal on 28.11.2016.
 */
@Singleton
@Module
public class AppModule {

    @NonNull
    private Context context;
    @NonNull
    private DB snappy;

    public AppModule(@NonNull Context context,@NonNull DB snappy) {
        this.context = context;
        this.snappy=snappy;
    }

    @Singleton
    @Provides
    Context provideContext() {
        return context;
    }

    @Singleton
    @Provides
    SharedPreferences provideSharedPreferences(){
        return context.getSharedPreferences(Constance.SharedPreferencesHolder.KEY, Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    DB provideDB(){return snappy;}
}
