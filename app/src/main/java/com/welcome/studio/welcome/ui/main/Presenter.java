package com.welcome.studio.welcome.ui.main;

import android.graphics.Bitmap;

import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.squareup.picasso.Picasso;

/**
 * Created by Royal on 05.01.2017.
 */

public interface Presenter {
    void onStart(boolean isAuth);

    boolean onDrawerItemCLick(android.view.View view, int position, IDrawerItem drawerItem);

    boolean onHeaderClick(android.view.View view);

    void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from);
}
