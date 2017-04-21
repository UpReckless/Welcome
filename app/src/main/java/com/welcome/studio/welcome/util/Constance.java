package com.welcome.studio.welcome.util;


import android.os.Environment;

public class Constance {
    public static class ConstHolder {
        public static final int MAX_POST_LIMIT = 20;
        public static final int EMPTY_LIST_COUNT = 0;
        public static final int RETRY_COUNT = 3;
        public static final int MAX_HISTORY_COLUMNS = 3;

        public static final int MAX_USER_LIMIT=50;
    }

    public static class URL {
        public static final String HOST = "http://env-4294164.mycloud.by/wlcome/";
        public static final String CHECK_SERVER_CONNECTION = "user/connection";

        public static final String USER_REG = "user/reg";
        public static final String USER_AUTH = "user/auth";
        public static final String USER_GET_ALL = "user/getUsers";
        public static final String USER_GET_QUERY = "user/getQueryUsers";
        public static final String USER_GET = "user/getUser";
        public static final String USER_UPDATE = "user/update";
        public static final String USER_CHECK_DUPLICATE = "user/checkname";

        public static final String GET_NOW_USER_POSTS = "post/getNowPosts";
        public static final String GET_WILLCOME_USER_POSTS = "post/getWillcomePosts";
        public static final String GET_HISTORY_USER_POSTS = "post/getHistoryPosts";

        public static final String RATING_GET = "rating/get";

        public static final String FIREBASE_STORAGE = "gs://welcomepush-96f73.appspot.com";
        public static final String FIREBASE_DATABASE = "https://welcomepush-96f73.firebaseio.com/";
    }

    public static class IntentCodeHolder {
        public static final int LOAD_PHOTO_FROM_GALLERY = 500;
        public static final int CAMERA_CODE = 501;

        public static final int NOTIFICATION_WILLCOME_CODE=100;
    }

    public static class IntentKeyHolder {
        public static final String POST_KEY = "post";
        public static final String NOTIFICATION_KEY="new_notification";
    }

    public static class SharedPreferencesHolder {
        public static final String KEY = "pref";
        //user
        public static final String NAME = "name";
        public static final String IMEI = "imei";
        public static final String EMAIL = "email";
        public static final String ID = "id";
        public static final String PHOTO_PATH = "photopath";
        public static final String PLACE = "city";
        public static final String COUNTRY = "country";
        public static final String PHOTO_REF = "photoref";
        public static final String RATING = "rating";
        public static final String TOKEN = "token";
        public static final String LANGUAGE = "language";
        //post
        public static final String TMP_PHOTO = "tmp_photo";
        public static final String POST = "post";
    }


    public static class FragmentTagHolder {
        public static final String WALL = "wall";
        public static final String PROFILE = "profile";
        public static final String RATING = "rating";
        public static final String VIP = "vip";
        public static final String SEARCH = "search";
        public static final String SETTINGS = "settings";
        public static final String CITY = "city";
        public static final String PHOTO = "photo";
        public static final String REGISTRY = "registry";
        public static final String SIGN_UP = "sign_up";
    }

    public static class AppDirectoryHolder {
        private static final String APP_PHOTO_DIR_PATH = Environment.getExternalStorageDirectory() + "/Welcome/";
        public static final String MAIN_PHOTO_DIR_PATH = APP_PHOTO_DIR_PATH + "Image";
        public static final String HISTORY_PHOTO_DIR_PATH = APP_PHOTO_DIR_PATH + "Photo";
    }

    public enum Language {
        RUSSIAN, ENGLISH;

        public static Language enumToInteger(int x) {
            switch (x) {
                case 0:
                    return RUSSIAN;
                case 1:
                    return ENGLISH;
                default:
                    return ENGLISH;
            }
        }
    }

    public enum PostType {
        NORMAL, VIP
    }

    public enum ContentType {
        PHOTO, VIDEO
    }
}
