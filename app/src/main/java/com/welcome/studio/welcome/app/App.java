package com.welcome.studio.welcome.app;

import android.app.Application;

import com.welcome.studio.welcome.model.NetworkModule;
import com.welcome.studio.welcome.util.UtilsModule;


public class App extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent=buildComponent();
        appComponent.inject(this);
    }

    public static AppComponent getComponent(){
        return appComponent;
    }

    protected AppComponent buildComponent(){
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .utilsModule(new UtilsModule())
                .build();
    }
}
