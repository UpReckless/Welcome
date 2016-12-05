package com.welcome.studio.welcome.presenter.impl;

import android.content.Context;

import com.welcome.studio.welcome.presenter.MainChildProfilePresenter;
import com.welcome.studio.welcome.view.fragment.MainChildProfileFragment;
import com.welcome.studio.welcome.view.fragment.adapter.ArchivePhotoModel;

/**
 * Created by Royal on 27.11.2016.
 */

public class MainChildProfilePresenterImpl implements MainChildProfilePresenter {

    private MainChildProfileFragment view;
    private Context context;
    private ArchivePhotoModel archivePhotoModel;

    public MainChildProfilePresenterImpl(MainChildProfileFragment view,Context context){
        this.view=view;
        this.context=context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }
}
