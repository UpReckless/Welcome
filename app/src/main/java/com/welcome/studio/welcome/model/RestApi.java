package com.welcome.studio.welcome.model;

import com.welcome.studio.welcome.model.data.Rating;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.model.entity.AuthRequest;
import com.welcome.studio.welcome.model.entity.AuthResponse;
import com.welcome.studio.welcome.model.entity.RegistryRequest;
import com.welcome.studio.welcome.model.entity.RegistryResponse;
import com.welcome.studio.welcome.model.entity.UpdateUserRequest;
import com.welcome.studio.welcome.model.entity.UpdateUserResponse;
import com.welcome.studio.welcome.util.Constance;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by @mistreckless on 18.10.2016. !
 */
public interface RestApi {
    @POST(Constance.URL.USER_REG)
    Observable<RegistryResponse> regUser(@Body RegistryRequest request);

    @POST(Constance.URL.USER_AUTH)
    Observable<AuthResponse> authUser(@Body AuthRequest authRequest);

    @POST(Constance.URL.USER_UPDATE)
    Observable<UpdateUserResponse> updateUser(@Body UpdateUserRequest request);

    @GET(Constance.URL.USER_GET_ALL)
    Observable<List<User>> getAllUsers();

    @GET(Constance.URL.RATING_GET + "{id}")
    Observable<Rating> getRating(@Path("id") long id);

    @GET(Constance.URL.USER_CHECK_DUPLICATE + "{name}")
    Observable<Boolean> checkDuplicate(@Path("name") String name);

    @GET(Constance.URL.CHECK_SERVER_CONNECTION)
    Observable<Boolean> checkServerConnection();
}
