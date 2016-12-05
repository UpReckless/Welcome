package com.welcome.studio.welcome.dagger.module;

import android.content.Context;

import com.welcome.studio.welcome.model.entity.DaoMaster;
import com.welcome.studio.welcome.model.entity.DaoSession;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Royal on 29.11.2016.
 */
@Module
public class DbModule {

    private Context context;
    public DbModule(Context context){
        this.context=context;
    }

    @Provides
    @Singleton
    DaoSession daoSession(){
        return new DaoMaster(new DaoMaster.DevOpenHelper(context,"welcomedb").getWritableDb()).newSession();
    }
}
