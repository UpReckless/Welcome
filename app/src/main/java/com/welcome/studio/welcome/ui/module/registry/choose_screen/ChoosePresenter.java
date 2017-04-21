package com.welcome.studio.welcome.ui.module.registry.choose_screen;

import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.module.registry.RegistryRouter;

import javax.inject.Inject;



public class ChoosePresenter extends BasePresenter<ChooseView,RegistryRouter>  {

    @Inject
    public ChoosePresenter(){}

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    public void userVisibleHint(boolean isVisibleToUser) {
        if (!isVisibleToUser && getRouter()!=null)
            getRouter().resetFragmentAdapter();
    }
}
