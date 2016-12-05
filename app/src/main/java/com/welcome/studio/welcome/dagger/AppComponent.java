package com.welcome.studio.welcome.dagger;

import android.app.Application;

import com.welcome.studio.welcome.dagger.module.AppModule;
import com.welcome.studio.welcome.dagger.module.DbModule;
import com.welcome.studio.welcome.dagger.module.MainModule;
import com.welcome.studio.welcome.dagger.module.NetworkModule;
import com.welcome.studio.welcome.dagger.module.UtilsModule;
import com.welcome.studio.welcome.model.ModelServerImpl;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Royal on 28.11.2016.
 */
@Component(modules = {AppModule.class,NetworkModule.class,DbModule.class,UtilsModule.class})
@Singleton
public interface AppComponent {
    void inject(Application application);
    void inject(ModelServerImpl modelServer);
    MainComponent plus(MainModule mainModule);

}
