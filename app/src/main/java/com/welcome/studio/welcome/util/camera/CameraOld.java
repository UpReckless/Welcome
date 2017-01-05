package com.welcome.studio.welcome.util.camera;

import android.hardware.Camera;

/**
 * Created by Royal on 13.12.2016.
 */
@SuppressWarnings("deprication")
public class CameraOld implements CameraSupport {

    private Camera camera;

    @Override
    public CameraSupport open(int cameraId) {
        camera=Camera.open(cameraId);
        return this;
    }

    @Override
    public int getOrientation(int cameraId) {
        Camera.CameraInfo info=new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId,info);
        return info.orientation;
    }
}
