package com.welcome.studio.welcome.view.fragment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.welcome.studio.welcome.view.fragment.impl.CommerceWallFragmentImpl;
import com.welcome.studio.welcome.view.fragment.impl.MainWallFragmentImpl;
import com.welcome.studio.welcome.view.fragment.impl.MyWallFragmentImpl;

/**
 * Created by Royal on 18.11.2016.
 */

public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {
    private static final int PAGE_COUNT=3;
    public HomeFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:{
                return new MyWallFragmentImpl();
            }
            case 1:{
                return new MainWallFragmentImpl();
            }
            case 2:{
                return new CommerceWallFragmentImpl();
            }
        }
        throw new RuntimeException("Illegal fragment position "+position+" (HomeFragmentPagerAdapter)");
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:return "MyWall";
            case 1:return "MainWall";
            case 2:return "CommerceWall";
        }
        throw new RuntimeException("Illegal fragment position "+position+" exception (HomeFragmentPagerAdapter)");
    }
}
