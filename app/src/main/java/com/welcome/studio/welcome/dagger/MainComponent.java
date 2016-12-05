package com.welcome.studio.welcome.dagger;

import com.welcome.studio.welcome.dagger.module.FirstStartModule;
import com.welcome.studio.welcome.dagger.module.MainModule;
import com.welcome.studio.welcome.dagger.scope.MainScope;
import com.welcome.studio.welcome.model.ModelServerImpl;
import com.welcome.studio.welcome.presenter.impl.MainActivityPresenterImpl;
import com.welcome.studio.welcome.view.activity.MainActivityImpl;
import com.welcome.studio.welcome.view.fragment.HomeFragment;
import com.welcome.studio.welcome.view.fragment.ProfileFragment;

import dagger.Subcomponent;

/**
 * Created by Royal on 01.12.2016.
 */
@MainScope
@Subcomponent(modules = {MainModule.class})
public interface MainComponent {
    void inject(MainActivityImpl mainActivity);
    void inject(HomeFragment homeFragment);
    void inject(ProfileFragment profileFragment);
    FirstStartComponent plus(FirstStartModule firstStartModule);
    void inject(MainActivityPresenterImpl mainActivityPresenter);
}
