package com.welcome.studio.welcome.model;


import android.support.annotation.NonNull;

import com.welcome.studio.welcome.model.pojo.AuthRequest;
import com.welcome.studio.welcome.model.pojo.AuthResponse;
import com.welcome.studio.welcome.model.pojo.Rating;
import com.welcome.studio.welcome.model.pojo.User;

import java.util.List;

import rx.Observable;

/**
 * Created by Royal on 18.10.2016.
 */
public interface ModelServer {

    Observable<User> regUser(User user);
    Observable<AuthResponse> authUser(@NonNull AuthRequest authRequest);
    Observable<User> updateUser(User user);
    Observable<List<User>> getAllUsers();
    Observable<Rating> getRating(long id);
}
