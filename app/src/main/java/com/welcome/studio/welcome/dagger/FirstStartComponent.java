package com.welcome.studio.welcome.dagger;

import com.welcome.studio.welcome.dagger.module.FirstStartModule;
import com.welcome.studio.welcome.dagger.scope.FirstStartScope;
import com.welcome.studio.welcome.presenter.impl.LastPagePresenterImpl;
import com.welcome.studio.welcome.view.fragment.adapter.FirstFragmentPagerAdapter;
import com.welcome.studio.welcome.view.fragment.impl.LastPageFragmentImpl;

import dagger.Subcomponent;

/**
 * Created by Royal on 30.11.2016.
 */
@FirstStartScope
@Subcomponent(modules = {FirstStartModule.class})
public interface FirstStartComponent {
    void inject(FirstFragmentPagerAdapter firstFragmentPagerAdapter);
    void inject(LastPageFragmentImpl lastPageFragment);
    void inject(LastPagePresenterImpl lastPagePresenter);
}
