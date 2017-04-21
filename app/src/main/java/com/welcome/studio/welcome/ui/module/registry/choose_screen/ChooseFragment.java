package com.welcome.studio.welcome.ui.module.registry.choose_screen;

import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;

import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.ui.BaseMainFragment;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.Layout;
import com.welcome.studio.welcome.ui.module.registry.RegistryModule;
import com.welcome.studio.welcome.app.Injector;

import javax.inject.Inject;

/**
 * Created by Royal on 07.02.2017.
 */
@Layout(id = R.layout.fragment_choose_language)
public class ChooseFragment extends BaseMainFragment implements ChooseView {

    @Inject
    ChoosePresenter presenter;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (presenter != null)
            presenter.userVisibleHint(isVisibleToUser);
    }

    @Override
    protected Object getRouter() {
        return getParentFragment();
    }

    @NonNull
    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public String getFragmentTag() {
        return null;
    }

    @Override
    protected void inject() {
        Injector.getInstance().plus(new RegistryModule()).inject(this);
    }

    @Override
    public Toolbar getToolbar() {
        return null;
    }

    @Override
    public String getToolbarTitle() {
        return null;
    }
}
