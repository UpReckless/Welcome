package com.welcome.studio.welcome.presenter;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Royal on 20.10.2016.
 */
public interface LastPagePresenter {

    void onPhotoReturn(int requestCode, int resultCode, Intent data, Context context);
    void regBtnClick(String name,String email,String imei);

}
