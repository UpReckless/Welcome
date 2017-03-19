package com.welcome.studio.welcome.model.repository;

import android.content.Intent;
import android.graphics.Bitmap;

import com.welcome.studio.welcome.model.data.Rating;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.model.entity.AuthRequest;
import com.welcome.studio.welcome.model.entity.AuthResponse;
import com.welcome.studio.welcome.model.entity.RegistryRequest;
import com.welcome.studio.welcome.model.entity.RegistryResponse;
import com.welcome.studio.welcome.model.entity.UpdateUserRequest;
import com.welcome.studio.welcome.model.entity.UpdateUserResponse;

import rx.Observable;

/**
 * Created by @mistreckless on 08.02.2017. !
 */

public interface UserRepository {

    Observable<RegistryResponse> registryNewUser(RegistryRequest request);

    Observable<UpdateUserResponse> updateUser(UpdateUserRequest request);

    Observable<AuthResponse> authUser(AuthRequest authRequest);

    User getUserCache();

    Observable<Boolean> requestDuplicate(CharSequence name);

    Observable<Void> saveLanguage(int x);

    Observable<String> findPhoto(Intent data);

    void downloadMyMainPhotoBitmap(Bitmap bitmap);

    Observable<Rating> getRating(long id);

    Observable<Boolean> checkServerConnection();
}
