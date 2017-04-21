package com.welcome.studio.welcome.model.repository;

import android.content.Intent;

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

import java.util.List;

import rx.Observable;
import rx.Single;

/**
 * Created by @mistreckless on 08.02.2017. !
 */

public interface UserRepository {

    Observable<RegistryResponse> registryNewUser(RegistryRequest request);

    Observable<UpdateUserResponse> updateUser(UpdateUserRequest request);

    Observable<AuthResponse> authUser(AuthRequest authRequest);

    Single<UserResponse> getUser(long id);

    User getUserCache();

    Observable<Boolean> requestDuplicate(CharSequence name);

    Observable<Void> saveLanguage(int x);

    Observable<String> findPhoto(Intent data);

    Observable<Rating> getRating(long id);

    Observable<Boolean> checkServerConnection();

    void showWillcomeNotification(Post post);

    Observable<List<UserResponse>> getUsers(int index);

    Observable<List<UserResponse>> getUsers(String name);
}
