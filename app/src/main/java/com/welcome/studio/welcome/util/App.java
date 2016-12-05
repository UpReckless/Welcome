package com.welcome.studio.welcome.util;

import android.app.Application;

import com.welcome.studio.welcome.dagger.AppComponent;
import com.welcome.studio.welcome.dagger.module.AppModule;
import com.welcome.studio.welcome.dagger.DaggerAppComponent;
import com.welcome.studio.welcome.dagger.module.DbModule;
import com.welcome.studio.welcome.dagger.module.NetworkModule;
import com.welcome.studio.welcome.dagger.module.UtilsModule;
import com.welcome.studio.welcome.model.entity.DaoMaster;
import com.welcome.studio.welcome.model.entity.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Royal on 31.10.2016.
 */

public class App extends Application {

    private static DaoSession daoSession;
    private static AppComponent appComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper devOpenHelper=new DaoMaster.DevOpenHelper(this,"welcomedb");
        Database db=devOpenHelper.getWritableDb();
        daoSession=new DaoMaster(db).newSession();
        appComponent=buildComponent();
        appComponent.inject(this);
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
    public static AppComponent getComponent(){
        return appComponent;
    }

    protected AppComponent buildComponent(){
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .dbModule(new DbModule(this))
                .utilsModule(new UtilsModule())
                .build();
    }
}
