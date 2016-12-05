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
    private MyWallFragmentImpl myWallFragment;
    private MainWallFragmentImpl mainWallFragment;
    private CommerceWallFragmentImpl commerceWallFragment;
    public HomeFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:{
                if (myWallFragment==null)
                    myWallFragment=new MyWallFragmentImpl();
                return myWallFragment;
            }
            case 1:{
                if (mainWallFragment==null)
                    mainWallFragment=new MainWallFragmentImpl();
                return mainWallFragment;
            }
            case 2:{
                if (commerceWallFragment==null)
                    commerceWallFragment=new CommerceWallFragmentImpl();
                return commerceWallFragment;
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
