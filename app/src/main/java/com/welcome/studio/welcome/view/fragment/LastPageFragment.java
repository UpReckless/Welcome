package com.welcome.studio.welcome.view.fragment;

import com.welcome.studio.welcome.dagger.FirstStartComponent;

/**
 * Created by Royal on 20.10.2016.
 */
public interface LastPageFragment {

    void showToast(int strId);
    void showToast(String message);
    void drawPhoto(String path);
    void savePreferences(String imei,String name);
    void start(boolean isAuth);
    FirstStartComponent getComponent();
}
