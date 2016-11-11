package com.welcome.studio.welcome.presenter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Royal on 28.10.2016.
 */

public interface MainActivityPresenter {
    void onCreate(SharedPreferences sharedPreferences, boolean isAuth,Bundle savedInstanceState);
}
