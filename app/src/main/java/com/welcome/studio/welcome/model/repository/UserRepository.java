package com.welcome.studio.welcome.model.repository;

import android.content.Intent;
import android.graphics.Bitmap;

import com.welcome.studio.welcome.model.data.AuthRequest;
import com.welcome.studio.welcome.model.data.AuthResponse;
import com.welcome.studio.welcome.model.data.Rating;
import com.welcome.studio.welcome.model.data.User;

import rx.Observable;

/**
 * Created by Royal on 08.02.2017.
 */

public interface UserRepository {

    Observable<User> registryNewUser(String name, String imei, String city, String country);

    Observable<User> updateUser(User user);

    Observable<AuthResponse> authUser(AuthRequest authRequest);

    User getUserCache();

    Observable<Boolean> requestDuplicate(CharSequence name);

    Observable<Void> saveLanguage(int x);

    Observable<String> findPhoto(Intent data);

    void downloadMyMainPhotoBitmap(Bitmap bitmap);

    Observable<Rating> getRating(long id);
}
