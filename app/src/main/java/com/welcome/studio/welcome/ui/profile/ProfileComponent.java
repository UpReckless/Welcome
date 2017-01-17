package com.welcome.studio.welcome.ui.profile;

import com.welcome.studio.welcome.ui.profile.history.HistoryComponent;
import com.welcome.studio.welcome.ui.profile.history.HistoryModule;

import dagger.Subcomponent;

/**
 * Created by Royal on 15.01.2017.
 */
@ProfileScope
@Subcomponent(modules = ProfileModule.class)
public interface ProfileComponent {
    void inject(Profile profile);
    void inject(ProfilePresenterImpl presenter);
    HistoryComponent plus(HistoryModule module);
}
