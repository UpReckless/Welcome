package com.welcome.studio.welcome.ui.photo;

import com.welcome.studio.welcome.squarecamera_mock.CustomPhotoSettingsFragment;
import com.welcome.studio.welcome.ui.photo.filter_screen.FilterPreview;
import com.welcome.studio.welcome.ui.photo.preview_screen.Preview;
import com.welcome.studio.welcome.ui.photo.settings_screen.CustomPhotoSettings;

import dagger.Subcomponent;

/**
 * Created by Royal on 18.01.2017.
 */
@PhotoScope
@Subcomponent(modules = PhotoModule.class)
public interface PhotoComponent {
    void inject(Photo photo);
    void inject(Preview preview);
    void inject(FilterPreview filterPreview);
    void inject(CustomPhotoSettings customPhotoSettings);
    void inject(CustomPhotoSettingsFragment customPhotoSettingsFragment);
}
