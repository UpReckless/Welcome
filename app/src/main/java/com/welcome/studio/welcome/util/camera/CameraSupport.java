package com.welcome.studio.welcome.util.camera;

/**
 * Created by Royal on 13.12.2016.
 */

public interface CameraSupport {
    CameraSupport open(int cameraId);
    int getOrientation(int cameraId);
}
