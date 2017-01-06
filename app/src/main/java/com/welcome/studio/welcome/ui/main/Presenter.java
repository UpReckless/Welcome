package com.welcome.studio.welcome.ui.main;

/**
 * Created by Royal on 05.01.2017.
 */

public interface Presenter {
    void onCreate(boolean isAuth);
    void onStart();
    void onResume();
    void onDestroy();
}
