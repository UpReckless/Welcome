package com.welcome.studio.welcome.model.repository.impl;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.model.RestApi;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.Rating;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.model.entity.AuthRequest;
import com.welcome.studio.welcome.model.entity.AuthResponse;
import com.welcome.studio.welcome.model.entity.RegistryRequest;
import com.welcome.studio.welcome.model.entity.RegistryResponse;
import com.welcome.studio.welcome.model.entity.UpdateUserRequest;
import com.welcome.studio.welcome.model.entity.UpdateUserResponse;
import com.welcome.studio.welcome.model.entity.UserResponse;
import com.welcome.studio.welcome.model.repository.UserRepository;
import com.welcome.studio.welcome.ui.module.main.MainActivity;
import com.welcome.studio.welcome.util.Constance;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Single;

import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.COUNTRY;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.EMAIL;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.ID;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.IMEI;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.LANGUAGE;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.NAME;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.PHOTO_PATH;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.PHOTO_REF;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.PLACE;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.RATING;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.TOKEN;

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
    public Observable<RegistryResponse> registryNewUser(RegistryRequest request) {
        Log.e("UserRepo", "threadReg " + Thread.currentThread());
        return restApi.regUser(request)
                .doOnNext(response -> cacheMainUser(request, response));
    }

    @Override
    public Observable<UpdateUserResponse> updateUser(UpdateUserRequest request) {
        return restApi.updateUser(request)
                .doOnNext(response -> cacheUpdatableUser(request, response));

    }

    @Override
    public Observable<AuthResponse> authUser(AuthRequest authRequest) {
        cacheAuthRequest(authRequest);
        return restApi.authUser(authRequest)
                .doOnNext(this::cacheAuthResponse);
    }

    @Override
    public Single<UserResponse> getUser(long id) {
        return restApi.getUser(id);
    }

    @Override
    public User getUserCache() {
        long id = spf.getLong(ID, 0);
        String name = spf.getString(NAME, null);
        String imei = spf.getString(IMEI, null);
        String email = spf.getString(EMAIL, null);
        String photoPath = spf.getString(PHOTO_PATH, null);
        String photoRef = spf.getString(PHOTO_REF, null);
        String token = spf.getString(TOKEN, null);
        String city = spf.getString(PLACE, null);
        String country = spf.getString(COUNTRY, null);
        Rating rating = getRatingFromCache();
        return new User(id, name, email, photoRef, photoPath, imei, rating, city, token, country);
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
    public Observable<Rating> getRating(long id) {
        return restApi.getRating(id)
                .doOnNext(this::cacheRating);
    }

    @Override
    public Observable<Boolean> checkServerConnection() {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
            return restApi.checkServerConnection().onErrorReturn(throwable -> false);
        else return Observable.just(false);
    }

    @Override
    public void showWillcomeNotification(Post post) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(Constance.IntentKeyHolder.NOTIFICATION_KEY, Constance.IntentCodeHolder.NOTIFICATION_WILLCOME_CODE);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.wlcome_launcher)
                .setContentTitle("You're welcome!!!")
                .setContentText(post.getAuthor().getName() + " ждет вас")
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setPriority(Notification.PRIORITY_HIGH)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setContentIntent(PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT))
                .build();

        nm.notify(0, notification);
    }

    @Override
    public Observable<List<UserResponse>> getUsers(int index) {
        return restApi.getUsers(index);
    }

    @Override
    public Observable<List<UserResponse>> getUsers(String name) {
        return restApi.getUsers(name);
    }

    private void cacheMainUser(RegistryRequest request, RegistryResponse response) {
        spf.edit()
                .putString(NAME, request.getName())
                .putLong(ID, response.getId())
                .putString(IMEI, request.getImei())
                .apply();
        cacheRating(response.getRating());
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
        spf.edit()
                .putString(PLACE, authRequest.getCity())
                .putString(COUNTRY, authRequest.getCountry())
                .apply();
    }

    private void cacheAuthResponse(AuthResponse authResponse) {
        spf.edit()
                .putString(TOKEN, authResponse.getToken())
                .apply();
    }


    private void cacheUpdatableUser(UpdateUserRequest request, UpdateUserResponse response) {
        spf.edit()
                .putString(IMEI, request.getImei())
                .putString(NAME, response.getName())
                .putString(EMAIL, response.getEmail())
                .putString(PHOTO_REF, response.getPhotoRef())
                .apply();
    }

}
