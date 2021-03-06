package com.welcome.studio.welcome.util;


public class Constance {
    public static class BackgroundImageHolder {
        public static final String FIRST_START_FIRST_IMAGE = "first_fragment.png";
        public static final String FIRST_START_SECOND_IMAGE = "second_fragment.png";
        public static final String FIRST_START_THIRD_IMAGE = "third_fragment.png";
    }

    public static class URL {
        public static final String HOST = "http://192.168.3.112:8080/";
        public static final String USER_REG = "user/reg";
        public static final String USER_AUTH = "user/auth";
        public static final String USER_UPDATE = "user/update";

        public static final String FIREBASE_STORAGE = "gs://welcomepush-96f73.appspot.com";
        public static final String FIREBASE_DATABASE = "https://welcomepush-96f73.firebaseio.com/";
    }

    public static class IntentCodeHolder {
        public static final int LOAD_PHOTO_FROM_GALLERY = 500;
    }

    public static class IntentKeyHolder {
        public static final String KEY_IS_FIRST = "is_first";
    }

    public static class SharedPreferencesHolder {
        public static final String NAME = "name";
        public static final String IMEI = "imei";
    }

    public static class CallbackPermissionsHolder {
        public static final int REQUEST_READ_PHONE_STATE = 600;
        public static final int REQUEST_WRITE_EXTERNAL_STORAGE = 601;
    }

    public static class FragmentTagHolder {
        public static final String HOME_MAIN_TAG = "Home";
        public static final String PROFILE_MAIN_TAG = "Profile";
        public static final String PHOTO_MAIN_TAG = "Photo";
    }
}
