package com.welcome.studio.welcome.model;

import com.welcome.studio.welcome.model.entity.User;
import com.welcome.studio.welcome.model.repository.ServerRepositoryCreator;
import com.welcome.studio.welcome.model.repository.ServerRepository;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Royal on 18.10.2016.
 */
public class ModelServerImpl implements ModelServer {

    private ServerRepository serverRepository = ServerRepositoryCreator.getUserRepository();

    @Override
    public Observable<User> regUser(User user) {
        return serverRepository.regUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<String> authUser(String imei) {
        return serverRepository.authUser(imei)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<User> updateUser(User user) {
        return serverRepository.updateUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
