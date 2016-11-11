package com.welcome.studio.welcome.view.fragment.firststart;

import android.graphics.Bitmap;

/**
 * Created by Royal on 20.10.2016.
 */
public interface LastPageFragment {

    void showToast(int strId);
    void showToast(String message);
    void drawPhoto(Bitmap bitmap);
    void savePreferences(String imei,String name);
    void start(boolean isAuth);
}
