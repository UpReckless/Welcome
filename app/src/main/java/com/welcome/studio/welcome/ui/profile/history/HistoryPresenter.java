package com.welcome.studio.welcome.ui.profile.history;

import android.graphics.Bitmap;

import com.squareup.picasso.Picasso;

/**
 * Created by Royal on 16.01.2017.
 */
public interface HistoryPresenter {
    int getCount();

    void onGetView(int position);

    Object getItem(int position);

    void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from);

    void onActivityCreated();
}
