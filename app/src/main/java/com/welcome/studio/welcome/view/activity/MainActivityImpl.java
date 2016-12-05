package com.welcome.studio.welcome.view.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.dagger.FirstStartComponent;
import com.welcome.studio.welcome.dagger.MainComponent;
import com.welcome.studio.welcome.dagger.module.MainModule;
import com.welcome.studio.welcome.presenter.MainActivityPresenter;
import com.welcome.studio.welcome.util.App;
import com.welcome.studio.welcome.util.Constance;
import com.welcome.studio.welcome.util.DepthPagerTransformer;
import com.welcome.studio.welcome.view.fragment.adapter.FirstFragmentPagerAdapter;

import javax.inject.Inject;

import me.relex.circleindicator.CircleIndicator;

public class MainActivityImpl extends AppCompatActivity implements MainActivity {

    @Inject
    MainActivityPresenter presenter;
    private SpaceNavigationView spaceNavigationView;
    private MainComponent mainComponent;
    private FirstFragmentPagerAdapter firstFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainComponent= App.getComponent().plus(new MainModule(this));
        mainComponent.inject(this);
        presenter.onCreate(getPreferences(MODE_PRIVATE),
                getIntent().getBooleanExtra(Constance.IntentKeyHolder.KEY_IS_FIRST, false), savedInstanceState);

    }

    @Override
    public void setFirstStart() {
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar_FullScreen);
        setContentView(R.layout.first_start);
        firstFragmentPagerAdapter=new FirstFragmentPagerAdapter(getSupportFragmentManager(),this);
        ViewPager firstStartPager = (ViewPager) findViewById(R.id.first_start_pager);
        CircleIndicator circleIndicator = (CircleIndicator) findViewById(R.id.circle_indicator);
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

    @Override
    public void clearFirstComponent() {
        if (firstFragmentPagerAdapter!=null)firstFragmentPagerAdapter.clearComponent();
    }

    @Override
    public MainComponent getMainComponent() {
        return mainComponent;
    }

    public FirstStartComponent getFirstStartComponent(){
        return firstFragmentPagerAdapter.getFirstStartComponent();
    }
}
