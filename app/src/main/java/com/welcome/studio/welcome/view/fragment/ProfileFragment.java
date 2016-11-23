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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter=new ProfileFragmentPagerAdapter(getChildFragmentManager());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager viewPager=(ViewPager)view.findViewById(R.id.viewPager);
        SlidingTabLayout slidingTabLayout=(SlidingTabLayout)view.findViewById(R.id.sliding_tabs);
        slidingTabLayout.setDistributeEvenly(true);
        viewPager.setAdapter(adapter);
        slidingTabLayout.setViewPager(viewPager);
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
