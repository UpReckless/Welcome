package com.welcome.studio.welcome.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.ui.module.profile.history.History;
import com.welcome.studio.welcome.ui.module.profile.now.Now;

/**
 * Created by @mistreckless on 16.01.2017. !
 */

public class ProfileAdapter extends FragmentStatePagerAdapter {
    private static final int PAGE_COUNT=2;
    private String todayTitle;
    private String historyTitle;
    private History history;
    private Now now;
    private User user;

    public ProfileAdapter(FragmentManager fm, String todayTitle, String historyTitle, User user) {
        super(fm);
        this.todayTitle=todayTitle;
        this.historyTitle=historyTitle;
        this.user=user;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                if (now ==null)
                    now =Now.newInstance(user);
                return now;
            case 1:
                if (history==null)
                    history=History.newInstance(user);
                return history;
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
