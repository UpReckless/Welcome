package com.welcome.studio.welcome.ui.profile;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.ui.profile.history.History;
import com.welcome.studio.welcome.ui.profile.today.Today;

/**
 * Created by Royal on 16.01.2017.
 */

public class ProfileAdapter extends FragmentStatePagerAdapter {
    private static final int PAGE_COUNT=2;
    private Resources resources;
    private Today today;
    private History history;

    public ProfileAdapter(ProfileView view) {
        super(view.getChildFragmentManager());
        this.resources=view.getResources();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:{
                if (today==null) today=new Today();
                return today;
            }
            case 1:{
                if (history==null) history=new History();
                return history;
            }
        }
        throw new RuntimeException("ProfileAdapter: Illegal fragment position "+position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:return resources.getString(R.string.today);
            case 1:return resources.getString(R.string.history);
        }
        throw new RuntimeException("ProfileAdapter: Illegal fragment position "+ position);
    }
}
