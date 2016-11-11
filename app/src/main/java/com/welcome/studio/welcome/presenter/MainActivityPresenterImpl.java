package com.welcome.studio.welcome.presenter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.welcome.studio.welcome.model.ModelServer;
import com.welcome.studio.welcome.model.ModelServerImpl;
import com.welcome.studio.welcome.model.entity.DaoSession;
import com.welcome.studio.welcome.model.entity.RaitingDao;
import com.welcome.studio.welcome.model.entity.UserDao;
import com.welcome.studio.welcome.util.App;
import com.welcome.studio.welcome.util.AuthService;
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
    public void onCreate(SharedPreferences spf, boolean isAuth,Bundle savedInstanceState) {
        if (spf.contains(Constance.SharedPreferencesHolder.NAME) && spf.contains(Constance.SharedPreferencesHolder.IMEI)) {
            if (!isAuth) {
                String imei = spf.getString(Constance.SharedPreferencesHolder.IMEI, null);
                modelServer.authUser(imei).subscribe(token -> {
                    AuthService.auth(token)
                            .addOnCompleteListener(task -> view.start(savedInstanceState))
                            .addOnFailureListener(failure -> Log.e("Fail to auth", failure.getMessage()));
                }, failure -> {
                    Log.e("MainActivityPres", failure.getMessage());
                });
            } else {
                view.start(savedInstanceState);
            }
        } else view.setFirstStart();
    }
}
