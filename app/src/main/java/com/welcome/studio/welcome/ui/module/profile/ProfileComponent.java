package com.welcome.studio.welcome.ui.module.profile;

import com.welcome.studio.welcome.ui.module.profile.history.History;
import com.welcome.studio.welcome.ui.module.profile.now.Now;

import dagger.Subcomponent;

/**
 * Created by Royal on 15.01.2017. !
 */
@ProfileScope
@Subcomponent(modules = ProfileModule.class)
public interface ProfileComponent {
    void inject(Profile profile);
    void inject(History history);
    void inject(Now now);
}
