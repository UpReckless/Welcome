package com.welcome.studio.welcome.model.repository;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.welcome.studio.welcome.model.RestApi;
import com.welcome.studio.welcome.model.data.AuthRequest;
import com.welcome.studio.welcome.model.data.AuthResponse;
import com.welcome.studio.welcome.model.data.Rating;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.util.Constance;
import com.welcome.studio.welcome.util.Helper;

import java.io.IOException;

import javax.inject.Inject;

import rx.Observable;

import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.COUNTRY;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.EMAIL;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.ID;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.IMEI;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.LANGUAGE;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.NAME;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.PHOTO_PATH;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.PHOTO_REF;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.RATING;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.TOKEN;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.TOWN;

/**
 * Created by @mistreckless on 08.02.2017. !
 */

public class UserRepositoryImpl implements UserRepository {
    private RestApi restApi;
    private SharedPreferences spf;
    private Context context;

    @Inject
    public UserRepositoryImpl(RestApi restApi, SharedPreferences spf, Context context) {
        this.restApi = restApi;
        this.spf = spf;
        this.context = context;
    }

    @Override
    public Observable<User> registryNewUser(String name,String imei,String city,String country) {
        spf.edit()
                .putString(IMEI, imei)
                .putString(NAME,name)
                .putString(TOWN,city)
                .putString(COUNTRY,country)
                .apply();
        Log.e("UserRepo","threadReg "+Thread.currentThread());
        return restApi.regUser(new User(name,imei,city,country))
                .doOnNext(this::cacheMainUser);
    }

    @Override
    public Observable<User> updateUser(User user) {
        spf.edit().putString(PHOTO_REF,user.getPhotoRef()).apply();
        return restApi.updateUser(user);

    }

    @Override
    public Observable<AuthResponse> authUser(AuthRequest authRequest) {
        cacheAuthRequest(authRequest);
        return restApi.authUser(authRequest)
                .doOnNext(this::cacheAuthResponse);
    }

    @Override
    public User getUserCache() {
        long id = spf.getLong(ID, 0);
        String name = spf.getString(NAME, null);
        String imei = spf.getString(IMEI, null);
        String email = spf.getString(EMAIL, null);
        String photoPath = spf.getString(PHOTO_PATH, null);
        String photoRef = spf.getString(PHOTO_REF, null);
        String city = spf.getString(TOWN, null);
        String country=spf.getString(COUNTRY,null);
        String token=spf.getString(TOKEN,null);
        Rating rating = getRatingFromCache();
        return new User(id, name, email, photoRef, photoPath, imei, rating,city,token,country);
    }

    @Override
    public Observable<Boolean> requestDuplicate(CharSequence name) {
        return restApi.checkDuplicate(name.toString());
    }

    @Override
    public Observable<Void> saveLanguage(int x) {
        return Observable.create(subscriber -> {
            spf.edit().putInt(LANGUAGE, x).apply();
            subscriber.onCompleted();
        });
    }

    @Override
    public Observable<String> findPhoto(Intent data) {
        return Observable.create(subscriber -> {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            try (Cursor cursor = context.getContentResolver().query(selectedImage, filePathColumn, null, null, null)) {
                if (cursor != null) {
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String pathToPhoto = cursor.getString(columnIndex);
                    spf.edit().putString(PHOTO_PATH, pathToPhoto).apply();
                    subscriber.onNext(pathToPhoto);
                    subscriber.onCompleted();
                }
            }
        });
    }

    @Override
    public void downloadMyMainPhotoBitmap(Bitmap bitmap) {
        try {
            String photoPath = Helper.savePhotoToDirectory(bitmap, Constance.AppDirectoryHolder.MAIN_PHOTO_DIR_PATH);
            spf.edit().putString(PHOTO_PATH, photoPath).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Observable<Rating> getRating(long id) {
        return restApi.getRating(id)
                .doOnNext(this::cacheRating);
    }

    private void cacheMainUser(User user) {
        spf.edit()
                .putString(NAME, user.getNickname())
                .putLong(ID, user.getId())
                .apply();
        cacheRating(user.getRating());
    }

    private void cacheRating(Rating rating) {
        try {
            spf.edit().putString(RATING, new ObjectMapper().writeValueAsString(rating)).apply();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private Rating getRatingFromCache() {
        String rating = spf.getString(RATING, null);
        if (rating != null)
            try {
                return new ObjectMapper().readValue(rating, Rating.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        return null;
    }

    private void cacheAuthRequest(AuthRequest authRequest) {
        spf.edit().putString(COUNTRY,authRequest.getCountry())
                .putString(TOWN,authRequest.getCity())
                .apply();
    }

    private void cacheAuthResponse(AuthResponse authResponse) {
        spf.edit()
                .putString(TOKEN, authResponse.getToken())
                .apply();
    }

}
