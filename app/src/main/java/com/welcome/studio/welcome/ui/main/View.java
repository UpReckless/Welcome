package com.welcome.studio.welcome.ui.main;

import android.support.v7.widget.Toolbar;

import com.welcome.studio.welcome.model.data.User;

/**
 * Created by @mistreckless on 05.01.2017. !
 */

public interface View {
    void setDrawer(User user);

//    void loadProfileImage(Picasso.Listener listener, String photoPath);
//
//    void loadProfileImage(Uri uri);
//
//    void loadProfileImage(@DrawableRes int res);

//    void updateProfile(IProfile profile);

    void setToolbarToDrawer(Toolbar toolbar, String title, boolean isAddedToBackStack);

}
