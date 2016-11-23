package com.welcome.studio.welcome.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.view.fragment.adapter.HomeFragmentPagerAdapter;

/**
 * Created by Royal on 11.11.2016.
 */

public class HomeFragment extends BaseFragment {
    private static String TAG="Home Fagment";
    private HomeFragmentPagerAdapter adapter;

    @Override
    public int getFragmentLayout() {
        return R.layout.home_fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        PagerTitleStrip pagerTitleStrip = (PagerTitleStrip) view.findViewById(R.id.pageTitle);
        pagerTitleStrip.setTextSize(TypedValue.COMPLEX_UNIT_DIP,24);
        pagerTitleStrip.setTextSpacing(100);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        adapter=new HomeFragmentPagerAdapter(getChildFragmentManager());
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        Log.e(TAG,"onAttachFragment");
        super.onAttachFragment(childFragment);
    }

}
