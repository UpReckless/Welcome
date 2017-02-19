package com.welcome.studio.welcome.util.tagview;

import android.util.Log;

/**
 * Created by @mistreckless on 2016/4/11. !
 */
class LogUtil {


    public static void v(String TAG, String msg) {
        if (!Constants.DEBUG) return;
        Log.v(TAG, msg);
    }

    public static void d(String TAG, String msg) {
        if (!Constants.DEBUG) return;
        Log.d(TAG, msg);
    }

    public static void i(String TAG, String msg) {
        Log.i(TAG, msg);
    }

    public static void w(String TAG, String msg) {
        Log.w(TAG, msg);
    }

    public static void e(String TAG, String msg) {
        Log.e(TAG, msg);
    }
}
