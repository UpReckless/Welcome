package com.welcome.studio.welcome.ui.wall;

import dagger.Subcomponent;

/**
 * Created by Royal on 18.01.2017.
 */
@WallScope
@Subcomponent(modules = WallModule.class)
public interface WallComponent {
    void inject(Wall wall);
}
