package com.welcome.studio.welcome.model;

import android.support.annotation.NonNull;

import com.welcome.studio.welcome.model.pojo.AuthRequest;
import com.welcome.studio.welcome.model.pojo.AuthResponse;
import com.welcome.studio.welcome.model.pojo.Rating;
import com.welcome.studio.welcome.model.pojo.User;
import com.welcome.studio.welcome.model.repository.ServerRepository;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ModelServerImpl implements ModelServer {

    private ServerRepository repository;
    public ModelServerImpl(ServerRepository repository){
        this.repository=repository;
    }

    @Override
    public Observable<User> regUser(User user) {
        return repository.regUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<AuthResponse> authUser(@NonNull AuthRequest authRequest) {
        return repository.authUser(authRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<User> updateUser(User user) {
        return repository.updateUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<User>> getAllUsers() {
        return repository.getAllUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Rating> getRating(long id) {
        return repository.getRating(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
