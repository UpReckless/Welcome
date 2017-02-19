package com.welcome.studio.welcome.ui.profile.history;

import android.graphics.Bitmap;

import com.squareup.picasso.Picasso;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.main.MainRouter;

import javax.inject.Inject;

/**
 * Created by Royal on 16.01.2017.
 */

public class HistoryPresenter extends BasePresenter<HistoryView,MainRouter> {

    @Inject
    HistoryPresenter(){

    }

    void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        if (from == Picasso.LoadedFrom.NETWORK){
            //save
        }
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
