package com.welcome.studio.welcome.ui.main;

import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.v4.app.FragmentManager;

import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by Royal on 05.01.2017.
 */

public interface View {
    MainComponent getComponent();
    void setDrawer(IProfile profile);
    FragmentManager getCurrentFragmentManager();
    void loadProfileImage(Picasso.Listener listener, Target target, String photoPath);
    void loadProfileImage(Target target, Uri uri);
    void loadProfileImage(Target target, @DrawableRes int res);
    void updateProfile(IProfile profile);
}
