package com.welcome.studio.welcome.ui.module.watchers.post;

import dagger.Subcomponent;

/**
 * Created by @mistreckless on 06.04.2017. !
 */
@PostWatcherScope
@Subcomponent(modules = PostWatcherModule.class)
public interface PostWatcherComponent {
    void inject(PostWatcher postWatcher);
}
