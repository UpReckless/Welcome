package com.welcome.studio.welcome.ui.module.watchers.author;

import dagger.Subcomponent;

/**
 * Created by @mistreckless on 12.04.2017. !
 */
@Subcomponent(modules = AuthorWatcherModule.class)
public interface AuthorWatcherComponent {
    void inject(AuthorWatcher authorWatcher);
}
