package com.welcome.studio.welcome.squarecamera_mock;

import dagger.Subcomponent;

/**
 * Created by Royal on 18.01.2017.
 */
@PhotoScope
@Subcomponent(modules = PhotoModule.class)
public interface PhotoComponent {
    void inject(CustomPhotoSettingsFragment customPhotoSettingsFragment);
}
