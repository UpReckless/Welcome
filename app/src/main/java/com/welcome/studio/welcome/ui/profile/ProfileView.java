package com.welcome.studio.welcome.ui.profile;

import android.content.res.Resources;
import android.support.v4.app.FragmentManager;

/**
 * Created by Royal on 12.01.2017.
 */

public interface ProfileView {
    ProfileComponent getComponent();
    void setData(String title, double rating,String city, long likes,long posts,long come,long vip);

    void loadMainPhoto(String photoPath);

    void setToolbar(String title);

    FragmentManager getChildFragmentManager();

    Resources getResources();
}
