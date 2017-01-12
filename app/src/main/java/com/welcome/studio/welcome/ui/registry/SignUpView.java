package com.welcome.studio.welcome.ui.registry;

import android.content.ContentResolver;

/**
 * Created by Royal on 06.01.2017.
 */

public interface SignUpView {
    RegistryComponent getComponent();

    boolean permissionCheck(String permission);

    void sendIntentToGallery();

    void requestPermission(String[] permissions, int requestCode);

    ContentResolver getContentResolver();

    void drawPhoto(String pathToPhoto);

    String getImei();

    String getEdName();

    String getEdEmail();

    void showToast(int toast_error_message);

    void showToast(String message);

    void start();

    void close();
}
