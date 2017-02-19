package com.welcome.studio.welcome.ui.profile;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.welcome.studio.welcome.ui.profile.history.History;
import com.welcome.studio.welcome.ui.profile.today.Today;

/**
 * Created by Royal on 16.01.2017.
 */

public class ProfileAdapter extends FragmentStatePagerAdapter {
    private static final int PAGE_COUNT=2;
    private String todayTitle;
    private String historyTitle;

    public ProfileAdapter(FragmentManager fm, String todayTitle, String historyTitle) {
        super(fm);
        this.todayTitle=todayTitle;
        this.historyTitle=historyTitle;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Today();
            case 1:
                return new History();
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
            case 0:return todayTitle;
            case 1:return historyTitle;
        }
        throw new RuntimeException("ProfileAdapter: Illegal fragment position "+ position);
    }
}
