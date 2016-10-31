package com.welcome.studio.welcome.view.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.presenter.MainActivityPresenter;
import com.welcome.studio.welcome.presenter.MainActivityPresenterImpl;
import com.welcome.studio.welcome.util.Constance;
import com.welcome.studio.welcome.view.fragment.DepthPagerTransformer;
import com.welcome.studio.welcome.view.fragment.firststart.FirstFragmentPagerAdapter;

import me.relex.circleindicator.CircleIndicator;

public class MainActivityImpl extends AppCompatActivity implements MainActivity {

    private ViewPager firstStartPager;
    private FragmentPagerAdapter firstFragmentPagerAdapter;
    private CircleIndicator circleIndicator;
    private MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=new MainActivityPresenterImpl(this);
        presenter.onCreate(getPreferences(MODE_PRIVATE),getIntent().getBooleanExtra(Constance.IntentKeyHolder.KEY_IS_FIRST,false));
    }

    @Override
    public void setFirstStart() {
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar_FullScreen);
        setContentView(R.layout.first_start);
        firstStartPager=(ViewPager)findViewById(R.id.first_start_pager);
        circleIndicator=(CircleIndicator)findViewById(R.id.circle_indicator);
        firstFragmentPagerAdapter=new FirstFragmentPagerAdapter(getSupportFragmentManager());
        firstStartPager.setAdapter(firstFragmentPagerAdapter);
        firstStartPager.setPageTransformer(true,new DepthPagerTransformer());
        circleIndicator.setViewPager(firstStartPager);
    }

    @Override
    public void start() {
        setTheme(R.style.AppTheme);
        setContentView(R.layout.test_resources);
    }
}
