package com.welcome.studio.welcome.ui.registry;

import android.content.Intent;

/**
 * Created by Royal on 06.01.2017.
 */

public interface SignUpPresenter {
    void onBtnGoClick();

    void onImgPhotoTouch();

    void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
