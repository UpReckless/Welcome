package com.welcome.studio.welcome.ui.main;

import com.welcome.studio.welcome.ui.comment.CommentComponent;
import com.welcome.studio.welcome.ui.comment.CommentModule;
import com.welcome.studio.welcome.squarecamera_mock.PhotoComponent;
import com.welcome.studio.welcome.squarecamera_mock.PhotoModule;
import com.welcome.studio.welcome.ui.profile.ProfileComponent;
import com.welcome.studio.welcome.ui.profile.ProfileModule;
import com.welcome.studio.welcome.ui.profile.watcher.PostWatcher;
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
    void inject(PostWatcher postWatcher);
    RegistryComponent plus(RegistryModule registryModule);
    ProfileComponent plus(ProfileModule profileModule);
    WallComponent plus(WallModule wallModule);
    PhotoComponent plus(PhotoModule photoModule);
    CommentComponent plus(CommentModule commentModule);
}
