package com.welcome.studio.welcome.ui.main;

public class PresenterImpl implements Presenter {

    public PresenterImpl(View view){
        view.getComponent().inject(this);
    }
    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }
}
