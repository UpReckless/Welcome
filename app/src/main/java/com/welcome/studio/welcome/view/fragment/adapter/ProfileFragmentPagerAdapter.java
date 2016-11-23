package com.welcome.studio.welcome.view.fragment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.welcome.studio.welcome.view.fragment.impl.MainChildProfileFragmentImpl;
import com.welcome.studio.welcome.view.fragment.impl.SearchChildProfileFragmentImpl;

/**
 * Created by Royal on 18.11.2016.
 */

public class ProfileFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private static final int PAGE_COUNT=2;

    public ProfileFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:return new MainChildProfileFragmentImpl();
            case 1:return new SearchChildProfileFragmentImpl();
        }
        throw new RuntimeException("Illegal fragment position "+position+" (ProfileFragmentPagerAdapter)");
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:return "Profile";
            case 1:return "Search";
        }
        throw new RuntimeException("Illegal fragment position"+position+" (ProfileFragmentPagerAdapter)");
    }
}
