package com.welcome.studio.welcome.model.repository;

import com.welcome.studio.welcome.util.Constance;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Royal on 20.10.2016.
 */
public class ServerRepositoryCreator {

    public static ServerRepository getUserRepository(){
        return retrofit().create(ServerRepository.class);
    }
    public static Retrofit retrofit(){
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10,TimeUnit.SECONDS)
                .build();
        Retrofit.Builder builder=new Retrofit.Builder()
                .baseUrl(Constance.URL.HOST)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient);
        return builder.build();
    }
}
