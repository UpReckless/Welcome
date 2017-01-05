package com.welcome.studio.welcome.ui.main;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Royal on 30.11.2016.
 */
@Module
@MainScope
public class MainModule {
    @NonNull
    private View view;
    public MainModule(@NonNull View view){
        this.view=view;
    }
    @Provides
    @MainScope
    public View provideMainActivity(){return view;}

    @Provides
    @MainScope
    public Presenter providePresenter(View view){
        return new PresenterImpl(view);
    }

}
