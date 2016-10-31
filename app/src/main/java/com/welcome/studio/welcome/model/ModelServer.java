package com.welcome.studio.welcome.model;


import com.welcome.studio.welcome.model.entity.User;

import rx.Observable;

/**
 * Created by Royal on 18.10.2016.
 */
public interface ModelServer {

    Observable<User> regUser(User user);
    Observable<String> authUser(String imei);
    Observable<User> updateUser(User user);
}
