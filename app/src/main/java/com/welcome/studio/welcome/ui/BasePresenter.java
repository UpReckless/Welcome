package com.welcome.studio.welcome.ui;

/**
 * Created by @mistreckless on 04.02.2017. !
 */

public abstract class BasePresenter<V, R> {
    private V view;
    private R router;

    public abstract void onStart();

    public abstract void onStop();

    public void setView(V view) {
        this.view = view;
    }

    public V getView() {
        return view;
    }

    public R getRouter() {
        return router;
    }

    public void setRouter(R router) {
        this.router = router;
    }
}
