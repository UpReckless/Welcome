package com.welcome.studio.welcome.app;

import android.app.Application;

import com.welcome.studio.welcome.model.ModelServerImpl;
import com.welcome.studio.welcome.model.NetworkModule;
import com.welcome.studio.welcome.ui.main.MainComponent;
import com.welcome.studio.welcome.ui.main.MainModule;
import com.welcome.studio.welcome.util.UtilsModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Royal on 28.11.2016.
 */
@Component(modules = {AppModule.class,NetworkModule.class,UtilsModule.class})
@Singleton
public interface AppComponent {
    void inject(Application application);
    void inject(ModelServerImpl modelServer);
    MainComponent plus(MainModule mainModule);

}
