package com.welcome.studio.welcome.view.fragment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.welcome.studio.welcome.dagger.FirstStartComponent;
import com.welcome.studio.welcome.dagger.module.FirstStartModule;
import com.welcome.studio.welcome.view.activity.MainActivity;
import com.welcome.studio.welcome.view.fragment.FirstPageFragment;
import com.welcome.studio.welcome.view.fragment.LastPageFragment;
import com.welcome.studio.welcome.view.fragment.impl.LastPageFragmentImpl;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Royal on 19.09.2016.
 */
public class FirstFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int NUMBER_OF_PAGES = 4;
    @Inject
    @Named("first")
    FirstPageFragment firstFragment;
    @Inject
    @Named("second")
    FirstPageFragment secondFragment;
    @Inject
    @Named("third")
    FirstPageFragment thirdFragment;
    @Inject
    LastPageFragment lastPageFragment;
    private FirstStartComponent firstStartComponent;


    public FirstFragmentPagerAdapter(FragmentManager fragmentManager, MainActivity mainActivity) {
        super(fragmentManager);
        firstStartComponent= mainActivity.getMainComponent().plus(new FirstStartModule(new LastPageFragmentImpl()));
        firstStartComponent.inject(this);
    }
    public FirstStartComponent getFirstStartComponent(){
        return firstStartComponent;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                return firstFragment;
            }
            case 1: {
                return secondFragment;
            }
            case 2: {
                return thirdFragment;
            }
            case 3: {
                return (LastPageFragmentImpl)lastPageFragment;
            }
        }
        throw new RuntimeException();
    }

    @Override
    public int getCount() {
        return NUMBER_OF_PAGES;
    }

    public void clearComponent() {
        firstStartComponent=null;
    }
}
