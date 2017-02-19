package com.welcome.studio.welcome.ui.photo;

import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.main.MainRouter;

import javax.inject.Inject;

/**
 * Created by Royal on 18.01.2017.
 */

public class PhotoPresenter extends BasePresenter<PhotoView,MainRouter> {

    @Inject
    PhotoPresenter(){
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    void navigateToWall() {
        getRouter().navigateToWall();
    }

}
