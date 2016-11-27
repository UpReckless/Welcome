package com.welcome.studio.welcome.view.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

/**
 * Created by Royal on 28.10.2016.
 */

public interface MainActivity  {
    void setFirstStart();
    void start(Bundle savedInstanceState);
    void setNavigationMenuVisibility(boolean isVisible);
    void customBackPressed();
    void changeCurrentItem(int index);
    FragmentManager getCurrentFragmentManager();
    void setOnClickListener(boolean b);
}
