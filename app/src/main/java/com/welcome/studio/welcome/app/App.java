package com.welcome.studio.welcome.app;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.devs.acr.AutoErrorReporter;
import com.welcome.studio.welcome.BuildConfig;
import com.welcome.studio.welcome.model.NetworkModule;
import com.welcome.studio.welcome.util.UtilsModule;


public class App extends MultiDexApplication {

    private static AppComponent appComponent;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (!BuildConfig.DEBUG)
            AutoErrorReporter.get(this)
                    .setEmailAddresses("www.roal@mail.ru")
                    .setEmailSubject("Error from wlcome")
                    .start();
        appComponent = buildComponent();
        appComponent.inject(this);
    }

    public static AppComponent getComponent() {
        return appComponent;
    }

    protected AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .utilsModule(new UtilsModule())
                .build();
    }
}
