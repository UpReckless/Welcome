package com.welcome.studio.welcome.dagger.module;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.welcome.studio.welcome.dagger.scope.FirstStartScope;
import com.welcome.studio.welcome.presenter.LastPagePresenter;
import com.welcome.studio.welcome.presenter.MainActivityPresenter;
import com.welcome.studio.welcome.presenter.impl.LastPagePresenterImpl;
import com.welcome.studio.welcome.presenter.impl.MainActivityPresenterImpl;
import com.welcome.studio.welcome.util.Constance;
import com.welcome.studio.welcome.view.activity.MainActivity;
import com.welcome.studio.welcome.view.fragment.FirstPageFragment;
import com.welcome.studio.welcome.view.fragment.LastPageFragment;
import com.welcome.studio.welcome.view.fragment.adapter.FirstFragmentPagerAdapter;
import com.welcome.studio.welcome.view.fragment.impl.LastPageFragmentImpl;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Royal on 30.11.2016.
 */
@Module
@FirstStartScope
public class FirstStartModule {
    private LastPageFragment lastPageFragment;
    public FirstStartModule(LastPageFragment lastPageFragment){
        this.lastPageFragment=lastPageFragment;
    }

    @Provides
    @FirstStartScope
    LastPageFragment provideLastPageFragment() {
        return lastPageFragment;
    }

    @Provides
    @FirstStartScope
    LastPagePresenter provideLastPagePresenter(LastPageFragment lastPageFragment) {
        return new LastPagePresenterImpl(lastPageFragment);
    }

    @Provides
    @Named("first")
    @FirstStartScope
    FirstPageFragment provideFirstPageFragment() {
        return FirstPageFragment.newInstance(Constance.BackgroundImageHolder.FIRST_START_FIRST_IMAGE);
    }

    @Provides
    @Named("second")
    @FirstStartScope
    FirstPageFragment provideSecondPageFragment() {
        return FirstPageFragment.newInstance(Constance.BackgroundImageHolder.FIRST_START_SECOND_IMAGE);
    }

    @Provides
    @Named("third")
    @FirstStartScope
    FirstPageFragment provideThirdPageFragment() {
        return FirstPageFragment.newInstance(Constance.BackgroundImageHolder.FIRST_START_THIRD_IMAGE);
    }

}
