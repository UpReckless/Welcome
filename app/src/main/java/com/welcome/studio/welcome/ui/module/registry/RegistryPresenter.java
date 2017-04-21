package com.welcome.studio.welcome.ui.module.registry;

import com.welcome.studio.welcome.model.interactor.RegistryInteractor;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.module.main.MainRouter;

import javax.inject.Inject;

/**
 * Created by Royal on 07.02.2017.
 */

public class RegistryPresenter extends BasePresenter<RegistryView,MainRouter> {

    private RegistryInteractor registryInteractor;

    @Inject
    RegistryPresenter(RegistryInteractor registryInteractor){
        this.registryInteractor=registryInteractor;
    }

    @Override
    public void onStart() {
        getView().setFragmentPagerAdapter();
    }

    @Override
    public void onStop() {

    }

    void setLanguage(int currentItem) {
        registryInteractor.setLanguage(currentItem)
                .subscribe();

    }

    void leaveRegistryModule() {
        getRouter().navigateFromRegistry();
    }
}
