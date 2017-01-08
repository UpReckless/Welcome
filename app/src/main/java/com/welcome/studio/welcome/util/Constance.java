package com.welcome.studio.welcome.util;


import android.os.Environment;

import java.io.File;

public class Constance {
    public static class BackgroundImageHolder {

    }

    public static class URL {
        public static final String HOST = "http://192.168.0.101:8080/";
        public static final String USER_REG = "user/reg";
        public static final String USER_AUTH = "user/auth";
        public static final String USER_GET_ALL ="user/getAll" ;
        public static final String USER_UPDATE = "user/update";

        public static final String FIREBASE_STORAGE = "gs://welcomepush-96f73.appspot.com";
        public static final String FIREBASE_DATABASE = "https://welcomepush-96f73.firebaseio.com/";
    }

    public static class IntentCodeHolder {
        public static final int LOAD_PHOTO_FROM_GALLERY = 500;
    }

    public static class IntentKeyHolder {
        public static final String KEY_IS_AUTH = "is_auth";
    }

    public static class SharedPreferencesHolder {
        public static final String NAME = "name";
        public static final String IMEI = "imei";
        public static final String ID = "id";
        public static final String KEY = "pref";
        public static final String PHOTO_PATH = "photopath";
        public static final String LAT = "latitude";
        public static final String LON = "longitude";
        public static final String CITY="city";
    }

    public static class CallbackPermissionsHolder {
        public static final int REQUEST_READ_PHONE_STATE = 600;
        public static final int REQUEST_WRITE_EXTERNAL_STORAGE = 601;
        public static final int REQUEST_LOCATION_ACCESS=602;
    }

    public static class FragmentTagHolder {

    }
    public static class AppDirectoryHolder{
        public static final File PHOTO_DIR= new File(Environment.getExternalStorageDirectory()+"/Welcome");
    }
}
