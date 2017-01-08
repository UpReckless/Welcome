package com.welcome.studio.welcome.ui.main;

import android.content.SharedPreferences;
import android.util.Log;

import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.model.ModelFirebase;
import com.welcome.studio.welcome.model.ModelServer;
import com.welcome.studio.welcome.model.pojo.AuthRequest;
import com.welcome.studio.welcome.ui.registry.SignUp;

import javax.inject.Inject;

import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.CITY;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.IMEI;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.LAT;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.LON;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.NAME;

public class PresenterImpl implements Presenter {
    private static final String TAG = "ui.main.PresenterImpl";
    private View view;
    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    ModelServer modelServer;
    @Inject
    ModelFirebase modelFirebase;

    @Inject
    public PresenterImpl(View view) {
        this.view = view;
        view.getComponent().inject(this);
    }

    @Override
    public void onCreate(boolean isAuth) {
        if (sharedPreferences.contains(NAME) && sharedPreferences.contains(IMEI)) {
            if (!isAuth)
                modelServer.authUser(new AuthRequest(Double.longBitsToDouble(sharedPreferences.getLong(LAT, 0)),
                        Double.longBitsToDouble(sharedPreferences.getLong(LON, 0)),sharedPreferences.getString(IMEI, null)))
                        .subscribe(authResponse -> {
                                    modelFirebase.auth(authResponse.getToken())
                                            .addOnCompleteListener(task -> Log.e(TAG, task.toString()))
                                            .addOnFailureListener(fail -> Log.e(TAG, fail.getMessage()));
                                    sharedPreferences.edit().putString(CITY, authResponse.getCity()).apply();

                                }
                                , failure -> Log.e(TAG, failure.getMessage()));
            view.start();
        } else
            view.getCurrentFragmentManager().beginTransaction().add(R.id.container, new SignUp()).commitAllowingStateLoss();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }
}