package com.welcome.studio.welcome.ui.profile;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Royal on 15.01.2017.
 */
@ProfileScope
@Module
public class ProfileModule {
    private ProfileView view;

    public ProfileModule(ProfileView view) {
        this.view = view;
    }

    @ProfileScope
    @Provides
    public ProfileView provideProfileView(){return view;}

    @ProfileScope
    @Provides
    public ProfilePresenter provideProfilePresenter(ProfileView view){return new ProfilePresenterImpl(view);}

    @ProfileScope
    @Provides
    public ProfileAdapter provideProfileAdapter(ProfileView view){return new ProfileAdapter(view);}
}
