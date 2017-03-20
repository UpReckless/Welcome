package com.welcome.studio.welcome.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.welcome.studio.welcome.util.Constance;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by @mistreckless on 28.11.2016. !
 */
@Singleton
@Module
public class AppModule {

    @NonNull
    private Context context;

    public AppModule(@NonNull Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    Context provideContext() {
        return context;
    }

    @Singleton
    @Provides
    SharedPreferences provideSharedPreferences() {
        return context.getSharedPreferences(Constance.SharedPreferencesHolder.KEY, Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    RxBus provideBus() {
        return new RxBus();
    }
}
