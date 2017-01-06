package com.welcome.studio.welcome.ui.main;

import android.support.v4.app.FragmentManager;

/**
 * Created by Royal on 05.01.2017.
 */

public interface View {
    MainComponent getComponent();
    void start();
    FragmentManager getCurrentFragmentManager();
}
