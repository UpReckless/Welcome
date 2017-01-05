package com.welcome.studio.welcome.util.camera;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;

/**
 * Created by Royal on 13.12.2016.
 */

public class CameraNew implements CameraSupport {
    private CameraDevice cameraDevice;
    private CameraManager cameraManager;

    public CameraNew(Context context){
        cameraManager=(CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
    }

    @Override
    public CameraSupport open(int cameraId) {
        try {
            String[] cameraIds=cameraManager.getCameraIdList();
            cameraManager.openCamera(cameraIds[cameraId], new CameraDevice.StateCallback() {
                @Override
                public void onOpened(CameraDevice cameraDevice) {
                  CameraNew.this.cameraDevice=cameraDevice;
                }

                @Override
                public void onDisconnected(CameraDevice cameraDevice) {
                    CameraNew.this.cameraDevice=cameraDevice;
                }

                @Override
                public void onError(CameraDevice cameraDevice, int i) {
                    CameraNew.this.cameraDevice=cameraDevice;
                }
            },null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public int getOrientation(int cameraId) {
        try {
            String[] cameraIds=cameraManager.getCameraIdList();
            CameraCharacteristics characteristics=cameraManager.getCameraCharacteristics(cameraIds[cameraId]);
            return characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
        } catch (CameraAccessException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
