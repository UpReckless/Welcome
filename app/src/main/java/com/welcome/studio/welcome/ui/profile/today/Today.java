package com.welcome.studio.welcome.ui.profile.today;

import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;

import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.ui.BaseMainFragment;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.Layout;
import com.welcome.studio.welcome.ui.profile.ProfileModule;
import com.welcome.studio.welcome.app.Injector;

import javax.inject.Inject;

/**
 * Created by Royal on 16.01.2017.
 */
@Layout(id= R.layout.fragment_today)
public class Today extends BaseMainFragment implements TodayView {

    @Inject
    TodayPresenter presenter;

    @Override
    protected Object getRouter() {
        return getActivity();
    }

    @NonNull
    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public String getFragmentTag() {
        return getTag();
    }

    @Override
    protected void inject() {
        Injector.getInstance().plus(new ProfileModule()).inject(this);
    }

    @Override
    protected Toolbar getToolbar() {
        return null;
    }

    @Override
    protected String getToolbarTitle() {
        return null;
    }
}
