package com.welcome.studio.welcome.model.repository;

import com.welcome.studio.welcome.model.entity.User;
import com.welcome.studio.welcome.util.Constance;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Royal on 18.10.2016.
 */
public interface ServerRepository {
    @POST(Constance.URL.USER_REG)
    Observable<User> regUser(@Body User user);

    @POST(Constance.URL.USER_AUTH)
    Observable<String> authUser(@Body String imei);

    @POST(Constance.URL.USER_UPDATE)
    Observable<User> updateUser(@Body User user);
}
