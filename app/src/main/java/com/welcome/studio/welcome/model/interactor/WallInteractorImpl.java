package com.welcome.studio.welcome.model.interactor;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Royal on 11.02.2017. !
 */

public class WallInteractorImpl implements WallInteractor {

    @Inject
    public WallInteractorImpl(){}

    @Override
    public Observable<Boolean> controlFab() {
        return Observable.just(true);
    }
}
