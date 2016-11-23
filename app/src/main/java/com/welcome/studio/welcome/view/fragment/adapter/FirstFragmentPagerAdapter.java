package com.welcome.studio.welcome.view.fragment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.welcome.studio.welcome.util.Constance;
import com.welcome.studio.welcome.view.fragment.FirstPageFragment;
import com.welcome.studio.welcome.view.fragment.impl.LastPageFragmentImpl;

/**
 * Created by Royal on 19.09.2016.
 */
public class FirstFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int NUMBER_OF_PAGES=4;

    public FirstFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:{
                return FirstPageFragment.newInstance(Constance.BackgroundImageHolder.FIRST_START_FIRST_IMAGE);
            }
            case 1:{
                return FirstPageFragment.newInstance(Constance.BackgroundImageHolder.FIRST_START_SECOND_IMAGE);
            }
            case 2:{
                return FirstPageFragment.newInstance(Constance.BackgroundImageHolder.FIRST_START_THIRD_IMAGE);
            }
            case 3:{
                return LastPageFragmentImpl.newInstance();
            }
        }
        throw new RuntimeException();
    }

    @Override
    public int getCount() {
        return NUMBER_OF_PAGES;
    }
}
