package com.welcome.studio.welcome.app;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.devs.acr.AutoErrorReporter;
import com.esotericsoftware.kryo.Kryo;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;
import com.welcome.studio.welcome.BuildConfig;
import com.welcome.studio.welcome.model.NetworkModule;
import com.welcome.studio.welcome.util.UtilsModule;


public class App extends MultiDexApplication {

    private static AppComponent appComponent;
    private DB snappyDb;

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
        try {
            snappyDb = DBFactory.open(this, "welcomedb", new Kryo());
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public static AppComponent getComponent() {
        return appComponent;
    }

    protected AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this, snappyDb))
                .networkModule(new NetworkModule())
                .utilsModule(new UtilsModule())
                .build();
    }
}
