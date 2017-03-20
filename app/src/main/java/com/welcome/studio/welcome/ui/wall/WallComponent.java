package com.welcome.studio.welcome.ui.wall;

import dagger.Subcomponent;

/**
 * Created by @mistreckless on 18.01.2017. !
 */
@WallScope
@Subcomponent(modules = WallModule.class)
public interface WallComponent {
    void inject(Wall wall);
}
