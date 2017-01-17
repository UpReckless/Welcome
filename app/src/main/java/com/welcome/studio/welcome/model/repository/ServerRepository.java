package com.welcome.studio.welcome.model.repository;

import com.welcome.studio.welcome.model.pojo.AuthRequest;
import com.welcome.studio.welcome.model.pojo.AuthResponse;
import com.welcome.studio.welcome.model.pojo.Rating;
import com.welcome.studio.welcome.model.pojo.User;
import com.welcome.studio.welcome.util.Constance;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Royal on 18.10.2016.
 */
public interface ServerRepository {
    @POST(Constance.URL.USER_REG)
    Observable<User> regUser(@Body User user);

    @POST(Constance.URL.USER_AUTH)
    Observable<AuthResponse> authUser(@Body AuthRequest authRequest);

    @POST(Constance.URL.USER_UPDATE)
    Observable<User> updateUser(@Body User user);

    @GET(Constance.URL.USER_GET_ALL)
    Observable<List<User>> getAllUsers();

    @GET(Constance.URL.RATING_GET+"{id}")
    Observable<Rating> getRating(@Path("id") long id);
}
