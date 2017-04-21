package com.welcome.studio.welcome.ui.module.main;

import com.welcome.studio.welcome.squarecamera_mock.PhotoComponent;
import com.welcome.studio.welcome.squarecamera_mock.PhotoModule;
import com.welcome.studio.welcome.ui.module.comment.CommentComponent;
import com.welcome.studio.welcome.ui.module.comment.CommentModule;
import com.welcome.studio.welcome.ui.module.profile.ProfileComponent;
import com.welcome.studio.welcome.ui.module.profile.ProfileModule;
import com.welcome.studio.welcome.ui.module.registry.RegistryComponent;
import com.welcome.studio.welcome.ui.module.registry.RegistryModule;
import com.welcome.studio.welcome.ui.module.search.SearchComponent;
import com.welcome.studio.welcome.ui.module.search.SearchModule;
import com.welcome.studio.welcome.ui.module.wall.WallComponent;
import com.welcome.studio.welcome.ui.module.wall.WallModule;
import com.welcome.studio.welcome.ui.module.watchers.author.AuthorWatcherComponent;
import com.welcome.studio.welcome.ui.module.watchers.author.AuthorWatcherModule;
import com.welcome.studio.welcome.ui.module.watchers.post.PostWatcherComponent;
import com.welcome.studio.welcome.ui.module.watchers.post.PostWatcherModule;

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
    PostWatcherComponent plus(PostWatcherModule postWatcherModule);
    AuthorWatcherComponent plus(AuthorWatcherModule authorWatcherModule);
    SearchComponent plus(SearchModule searchModule);
}
