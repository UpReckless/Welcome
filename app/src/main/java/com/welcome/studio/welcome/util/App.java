package com.welcome.studio.welcome.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.welcome.studio.welcome.model.entity.DaoMaster;
import com.welcome.studio.welcome.model.entity.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Royal on 31.10.2016.
 */

public class App extends Application {

    private static DaoSession daoSession;


    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper devOpenHelper=new DaoMaster.DevOpenHelper(this,"welcomedb");
        Database db=devOpenHelper.getWritableDb();
        daoSession=new DaoMaster(db).newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

}
