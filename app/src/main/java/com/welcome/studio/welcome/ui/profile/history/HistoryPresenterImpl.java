package com.welcome.studio.welcome.ui.profile.history;

import android.graphics.Bitmap;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

/**
 * Created by Royal on 16.01.2017.
 */

public class HistoryPresenterImpl implements HistoryPresenter {
    private HistoryView view;
    @Inject
    AdapterView adapter;

    @Inject
    HistoryPresenterImpl(HistoryView view){
        this.view=view;
        view.getComponent().inject(this);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public void onGetView(int position) {

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        if (from == Picasso.LoadedFrom.NETWORK){
            adapter.setImage(bitmap);
            //save
        }
        else adapter.setImage(bitmap);
    }

    @Override
    public void onActivityCreated() {
        //init data
        view.setAdapter();
    }
}
