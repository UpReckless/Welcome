package com.welcome.studio.welcome.ui.main;

import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.v4.app.FragmentManager;

import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.squareup.picasso.Picasso;

/**
 * Created by Royal on 05.01.2017.
 */

public interface View {
    MainComponent getComponent();
    void setDrawer(IProfile profile);
    FragmentManager getCurrentFragmentManager();
    void loadProfileImage(Picasso.Listener listener, String photoPath);
    void loadProfileImage(Uri uri);
    void loadProfileImage(@DrawableRes int res);
    void updateProfile(IProfile profile);
    Resources getResources();

}
