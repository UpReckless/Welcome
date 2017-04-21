package com.welcome.studio.welcome.ui.module.registry.singup.last_screen;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.welcome.studio.welcome.app.Injector;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.model.interactor.MainInteractor;
import com.welcome.studio.welcome.model.interactor.RegistryInteractor;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.module.registry.RegistryRouter;
import com.welcome.studio.welcome.util.Constance;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * Created by Royal on 09.02.2017.
 */

class NextStepPresenter extends BasePresenter<NextStepView, RegistryRouter> {

    private RegistryInteractor registryInteractor;
    private Lazy<RxPermissions> rxPermissions;
    private MainInteractor mainInteractor;

    @Inject
    NextStepPresenter(RegistryInteractor registryInteractor, Lazy<RxPermissions> rxPermissions, MainInteractor mainInteractor) {
        this.registryInteractor = registryInteractor;
        this.rxPermissions = rxPermissions;
        this.mainInteractor = mainInteractor;
    }

    @Override
    public void onStart() {
        User user = registryInteractor.getUserCache();
        getView().setHeaderText("Welcome, " + user.getNickname());
    }

    @Override
    public void onStop() {

    }

    void finishRegistry() {
        rxPermissions.get().request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(granted -> {
                    if (!granted) getView().finish();
                    else mainInteractor.auth()
                            .subscribe(success -> {
                                if (success) {
                                    getRouter().navigateToMainScreen();
                                    if (registryInteractor.getUserCache().getPhotoPath() != null)
                                        mainInteractor.uploadMainPhoto().subscribe(res->{
                                            Injector.getInstance().clearRegistryComponent();});
                                } else getView().showToast("Something wrong");
                            },throwable -> getView().showToast("Huita 4to to ne tak"));
                });
    }

    void choosePhoto() {
        rxPermissions.get().request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(this::requestToGallery);
    }

    private void requestToGallery(boolean granted) {
        if (granted)
            getView().sendIntentToGallery();
    }

    void galleryResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constance.IntentCodeHolder.LOAD_PHOTO_FROM_GALLERY && resultCode == Activity.RESULT_OK
                && data != null)
            registryInteractor.controlMainPhoto(data)
                    .subscribe(getView()::setMainPhoto);
    }
}
