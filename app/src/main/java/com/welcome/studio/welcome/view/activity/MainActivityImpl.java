package com.welcome.studio.welcome.view.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.presenter.MainActivityPresenter;
import com.welcome.studio.welcome.presenter.MainActivityPresenterImpl;
import com.welcome.studio.welcome.util.Constance;
import com.welcome.studio.welcome.util.DepthPagerTransformer;
import com.welcome.studio.welcome.view.fragment.HomeFragment;
import com.welcome.studio.welcome.view.fragment.ProfileFragment;
import com.welcome.studio.welcome.view.fragment.adapter.FirstFragmentPagerAdapter;

import me.relex.circleindicator.CircleIndicator;

public class MainActivityImpl extends AppCompatActivity implements MainActivity {

    private MainActivityPresenter presenter;
    private SpaceNavigationView spaceNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MainActivityPresenterImpl(this);
        presenter.onCreate(getPreferences(MODE_PRIVATE),
                getIntent().getBooleanExtra(Constance.IntentKeyHolder.KEY_IS_FIRST, false), savedInstanceState);
    }

    @Override
    public void setFirstStart() {
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar_FullScreen);
        setContentView(R.layout.first_start);
        ViewPager firstStartPager = (ViewPager) findViewById(R.id.first_start_pager);
        CircleIndicator circleIndicator = (CircleIndicator) findViewById(R.id.circle_indicator);
        FragmentPagerAdapter firstFragmentPagerAdapter = new FirstFragmentPagerAdapter(getSupportFragmentManager());
        firstStartPager.setAdapter(firstFragmentPagerAdapter);
        firstStartPager.setPageTransformer(true, new DepthPagerTransformer());
        circleIndicator.setViewPager(firstStartPager);
    }

    @Override
    public void start(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
        initSpaceNavigation(savedInstanceState);
        presenter.start();
    }

    @Override
    public void setNavigationMenuVisibility(boolean isVisible) {
        spaceNavigationView.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void customBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void changeCurrentItem(int index) {
        spaceNavigationView.changeCurrentItem(index);
        spaceNavigationView.setSpaceOnClickListener(presenter);
    }

    @Override
    public void onBackPressed() {
        if (spaceNavigationView != null) {
            spaceNavigationView.setSpaceOnClickListener(null);
            presenter.onBackPressed();
        } else super.onBackPressed();
    }

    private void initSpaceNavigation(Bundle savedInstanceState) {
        spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem(Constance.FragmentTagHolder.HOME_MAIN_TAG, R.mipmap.ic_launcher));
        spaceNavigationView.addSpaceItem(new SpaceItem(Constance.FragmentTagHolder.PROFILE_MAIN_TAG, R.mipmap.ic_launcher));
        spaceNavigationView.setSpaceOnClickListener(presenter);
    }

    @Override
    public FragmentManager getCurrentFragmentManager(){
        return getSupportFragmentManager();
    }

    @Override
    public void setOnClickListener(boolean b) {
        spaceNavigationView.setSpaceOnClickListener(b?presenter:null);
    }
}
