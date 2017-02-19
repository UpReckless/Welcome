package com.welcome.studio.welcome.app;

import android.support.multidex.MultiDexApplication;

import com.esotericsoftware.kryo.Kryo;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;
import com.welcome.studio.welcome.model.NetworkModule;
import com.welcome.studio.welcome.util.UtilsModule;


public class App extends MultiDexApplication {

    private static AppComponent appComponent;
    private DB snappyDb;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent=buildComponent();
        appComponent.inject(this);
        try {
            snappyDb= DBFactory.open(this,"welcomedb",new Kryo());
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public static AppComponent getComponent(){
        return appComponent;
    }

    protected AppComponent buildComponent(){
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this,snappyDb))
                .networkModule(new NetworkModule())
                .utilsModule(new UtilsModule())
                .build();
    }
}
