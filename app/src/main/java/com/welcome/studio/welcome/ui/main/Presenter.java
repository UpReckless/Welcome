package com.welcome.studio.welcome.ui.main;

import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

/**
 * Created by Royal on 05.01.2017.
 */

public interface Presenter {
    void onCreate(boolean isAuth);
    void onDestroy();

    boolean onDrawerItemCLick(android.view.View view, int position, IDrawerItem drawerItem);

    boolean onHeaderClick(android.view.View view);
}
