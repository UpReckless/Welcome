package com.welcome.studio.welcome.ui.photo.filter_screen;

import android.graphics.Bitmap;
import android.util.Log;

import com.welcome.studio.welcome.model.interactor.PhotoInteractor;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.photo.PhotoRouter;

import java.io.IOException;

import javax.inject.Inject;

/**
 * Created by Royal on 11.02.2017. !
 */

class FilterPresenter extends BasePresenter<FilterPreviewView, PhotoRouter> {

    private PhotoInteractor photoInteractor;

    @Inject
    FilterPresenter(PhotoInteractor photoInteractor) {
        this.photoInteractor = photoInteractor;
    }

    @Override
    public void onStart() {
        getView().initUiWidgets();
    }

    @Override
    public void onStop() {

    }

    void next(Bitmap outputImage) {
        try {
            photoInteractor.saveFinishedPicture(outputImage)
                    .subscribe(getRouter()::navigateToCustomPhotoSettingsScreen, e -> Log.e("FilterPres", e.getMessage()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
