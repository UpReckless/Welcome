package com.welcome.studio.welcome.ui.photo.preview_screen;

import android.graphics.SurfaceTexture;
import android.util.Log;

import com.upreckless.support.portraitcamerasupport.CameraSupport;
import com.upreckless.support.portraitcamerasupport.CameraSupportController;
import com.upreckless.support.portraitcamerasupport.OnSupportCameraTakePictureListener;
import com.welcome.studio.welcome.model.interactor.PhotoInteractor;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.photo.PhotoRouter;

import javax.inject.Inject;

/**
 * Created by Royal on 11.02.2017. !
 */

class PreviewPresenter extends BasePresenter<PreviewView, PhotoRouter> {

    private CameraSupport cameraSupport;
    private PhotoInteractor photoInteractor;

    @Inject
    PreviewPresenter(PhotoInteractor photoInteractor) {
        this.photoInteractor = photoInteractor;
    }

    @Override
    public void onStart() {
        cameraSupport.setErrorListener(message -> Log.e("PreviewPres", message));

    }

    @Override
    public void onStop() {

    }

    void setCameraSupport(CameraSupport cameraSupport) {
        this.cameraSupport = cameraSupport;
    }

    void resume(SurfaceTexture surfaceTexture, int width, int height) {
        cameraSupport.resumePreview(surfaceTexture, width, height);
    }

    void pause() {
        cameraSupport.stopPreview();
    }

    void available(SurfaceTexture surface, int width, int height) {
        cameraSupport.startPreview(surface, width, height, cameraSupport.getCurrentCameraType() == null ?
                CameraSupportController.CameraType.BACK : cameraSupport.getCurrentCameraType());
    }

    void cameraReselect() {
        cameraSupport.changeCameraType(cameraSupport.getCurrentCameraType() == CameraSupportController.CameraType.BACK ?
                CameraSupportController.CameraType.FRONT : CameraSupportController.CameraType.BACK);
        getView().setFlashImage(false);
    }

    void flashClick() {
        if (cameraSupport.isFlashSupported()) {
            cameraSupport.setAutoFlashEnabled(!cameraSupport.isAutoFlashEnabled());
            getView().setFlashImage(cameraSupport.isAutoFlashEnabled());
        }
    }

    void takePicture() {
        cameraSupport.takePhoto(new OnSupportCameraTakePictureListener() {
            @Override
            public void onPicture(byte[] bytes) {
                photoInteractor.takeTmpPicture(cameraSupport, bytes)
                        .subscribe(getRouter()::navigateFilterScreen);
            }

            @Override
            public void onError(String message) {
                Log.e("PreviewPres", message);
            }
        });
    }
}
