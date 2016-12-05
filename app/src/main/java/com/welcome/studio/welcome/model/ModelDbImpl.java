package com.welcome.studio.welcome.model;

import com.welcome.studio.welcome.model.entity.User;
import com.welcome.studio.welcome.util.App;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Royal on 27.11.2016.
 */

public class ModelDbImpl implements ModelDb {
    @Override
    public Observable<User> getCurrentUser() {
        return Observable.just(App.getDaoSession().getUserDao().loadAll().get(0))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
