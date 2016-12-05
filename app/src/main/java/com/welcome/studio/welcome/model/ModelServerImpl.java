package com.welcome.studio.welcome.model;

import com.welcome.studio.welcome.model.entity.User;
import com.welcome.studio.welcome.model.repository.ServerRepositoryCreator;
import com.welcome.studio.welcome.model.repository.ServerRepository;
import com.welcome.studio.welcome.util.App;
import com.welcome.studio.welcome.view.activity.MainActivity;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ModelServerImpl implements ModelServer {

    @Inject
    ServerRepository userRepository;
    public ModelServerImpl(){
        App.getComponent().inject(this);
    }

    @Override
    public Observable<User> regUser(User user) {
        return userRepository.regUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<String> authUser(String imei) {
        return userRepository.authUser(imei)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<User> updateUser(User user) {
        return userRepository.updateUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
