package com.welcome.studio.welcome.ui.main;

import dagger.Subcomponent;

/**
 * Created by Royal on 01.12.2016.
 */
@MainScope
@Subcomponent(modules = {MainModule.class})
public interface MainComponent {
    void inject(MainActivity mainActivity);
    void inject(PresenterImpl presenter);
}
