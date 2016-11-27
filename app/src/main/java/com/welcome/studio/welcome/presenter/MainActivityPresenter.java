package com.welcome.studio.welcome.presenter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.luseen.spacenavigation.SpaceOnClickListener;

/**
 * Created by Royal on 28.10.2016.
 */

public interface MainActivityPresenter extends SpaceOnClickListener {
    void onCreate(SharedPreferences sharedPreferences, boolean isAuth, Bundle savedInstanceState);

    void onBackPressed();

    void start();

    @Override
    void onCentreButtonClick();

    @Override
    void onItemClick(int itemIndex, String itemName);

    @Override
    void onItemReselected(int itemIndex, String itemName);
}
