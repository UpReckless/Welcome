package com.welcome.studio.welcome.presenter;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.view.View;

import com.welcome.studio.welcome.model.ModelServer;
import com.welcome.studio.welcome.model.ModelServerImpl;
import com.welcome.studio.welcome.util.Constance;
import com.welcome.studio.welcome.view.activity.MainActivity;

/**
 * Created by Royal on 28.10.2016.
 */

public class MainActivityPresenterImpl implements MainActivityPresenter {

    private MainActivity view;
    private ModelServer modelServer;

    public MainActivityPresenterImpl(MainActivity view) {
        this.view = view;
        modelServer = new ModelServerImpl();
    }

    @Override
    public void onCreate(SharedPreferences spf, boolean isAuth) {
        if (spf.contains(Constance.SharedPreferencesHolder.NAME) && spf.contains(Constance.SharedPreferencesHolder.IMEI)) {
            if (!isAuth) {
                //Connect to Firebase
                view.start();
            } else {
                view.start();
            }
        } else view.setFirstStart();
    }
}
