package com.welcome.studio.welcome.ui.main;

import com.welcome.studio.welcome.ui.comment.CommentComponent;
import com.welcome.studio.welcome.ui.comment.CommentModule;
import com.welcome.studio.welcome.ui.photo.PhotoComponent;
import com.welcome.studio.welcome.ui.photo.PhotoModule;
import com.welcome.studio.welcome.ui.profile.ProfileComponent;
import com.welcome.studio.welcome.ui.profile.ProfileModule;
import com.welcome.studio.welcome.ui.registry.RegistryComponent;
import com.welcome.studio.welcome.ui.registry.RegistryModule;
import com.welcome.studio.welcome.ui.wall.WallComponent;
import com.welcome.studio.welcome.ui.wall.WallModule;

import dagger.Subcomponent;

/**
 * Created by @mistreckless on 01.12.2016. !
 */
@MainScope
@Subcomponent(modules = {MainModule.class})
public interface MainComponent {
    void inject(MainActivity mainActivity);
    RegistryComponent plus(RegistryModule registryModule);
    ProfileComponent plus(ProfileModule profileModule);
    WallComponent plus(WallModule wallModule);
    PhotoComponent plus(PhotoModule photoModule);
    CommentComponent plus(CommentModule commentModule);
}
