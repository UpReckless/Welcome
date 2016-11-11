package com.welcome.studio.welcome.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.presenter.MainActivityPresenter;
import com.welcome.studio.welcome.presenter.MainActivityPresenterImpl;
import com.welcome.studio.welcome.util.Constance;
import com.welcome.studio.welcome.view.fragment.DepthPagerTransformer;
import com.welcome.studio.welcome.view.fragment.firststart.FirstFragmentPagerAdapter;

import me.relex.circleindicator.CircleIndicator;

public class MainActivityImpl extends AppCompatActivity implements MainActivity, SpaceOnClickListener {

    private MainActivityPresenter presenter;
    private FragmentManager fragmentManager;

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
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.container, new Fragment(), "Home")
                .addToBackStack("Home")
                .commit();
    }

    @Override
    public void onCentreButtonClick() {
        Log.e("OnCentreBtnClick", "Hehe");
    }

    @Override
    public void onItemClick(int itemIndex, String itemName) {
        switch (itemName) {
            case "Home": {
                Log.e("onItemClick", "Home");
                break;
            }
            case "Profile": {
                Log.e("onItemClick", "Profile");
                break;
            }
        }
    }

    @Override
    public void onItemReselected(int itemIndex, String itemName) {
        Log.e("OnItemreselected", itemName);
    }

    private void initSpaceNavigation(Bundle savedInstanceState) {
        SpaceNavigationView spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("Home", R.mipmap.ic_launcher));
        spaceNavigationView.addSpaceItem(new SpaceItem("Profile", R.mipmap.ic_launcher));
        spaceNavigationView.setSpaceOnClickListener(this);
    }
}
