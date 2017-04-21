package com.welcome.studio.welcome.ui.module.profile;

import android.net.Uri;

import com.welcome.studio.welcome.model.data.Rating;

import java.io.File;

/**
 * Created by Royal on 12.01.2017.
 */

public interface ProfileView {
    void setData(String city, Rating rating);

    void loadMainPhoto(File photoFile);

    void loadMainPhoto(Uri uri);

    void updateData(Rating rating);
}
