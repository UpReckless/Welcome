package com.welcome.studio.welcome.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.util.SlidingTabLayout;
import com.welcome.studio.welcome.view.fragment.adapter.ProfileFragmentPagerAdapter;

/**
 * Created by Royal on 11.11.2016.
 */

public class ProfileFragment extends BaseFragment {

    private ProfileFragmentPagerAdapter adapter;
    ViewPager viewPager;
    SlidingTabLayout slidingTabLayout;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter=new ProfileFragmentPagerAdapter(getChildFragmentManager());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager=(ViewPager)view.findViewById(R.id.viewPager);
        slidingTabLayout=(SlidingTabLayout)view.findViewById(R.id.sliding_tabs);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewPager.setAdapter(adapter);
        if (savedInstanceState!=null){
            viewPager.setCurrentItem(savedInstanceState.getInt("position"));
        }
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position",viewPager.getCurrentItem());
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.profile_fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
