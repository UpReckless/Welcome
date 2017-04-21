package com.welcome.studio.welcome.ui.module.registry.singup.first_screen;

import android.Manifest;
import android.support.annotation.NonNull;
import android.util.Log;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.model.entity.ExceptionJSONInfo;
import com.welcome.studio.welcome.model.interactor.RegistryInteractor;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.module.registry.RegistryRouter;
import com.welcome.studio.welcome.ui.module.registry.RegistryScope;
import com.welcome.studio.welcome.util.ExceptionUtil;

import javax.inject.Inject;

import dagger.Lazy;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;

@RegistryScope
public class SignUpPresenter extends BasePresenter<SignUpView, RegistryRouter> {
    private static final String TAG = "SignUpPresenter";
    private RegistryInteractor registryInteractor;
    private Lazy<RxPermissions> rxPermissions;
    private String name = "";

    @Inject
    public SignUpPresenter(RegistryInteractor registryInteractor, Lazy<RxPermissions> rxPermissions) {
        this.registryInteractor = registryInteractor;
        this.rxPermissions = rxPermissions;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {

    }

    void onAlreadyRegisteredClick() {
        getRouter().navigateRestoreAccount();
    }

    void listenField(@NonNull Observable<CharSequence> nameFieldListener) {
        registryInteractor.controlHeaderHelloView(nameFieldListener)
                .doOnNext(charSequence -> getView().showProgressBarVisible())
                .map(name -> "Hello" + name)
                .subscribe(getView()::setHeaderName, throwable ->
                        Log.e(TAG, throwable.getMessage()));
        registryInteractor.controlNextButton(nameFieldListener)
                .subscribe(getView()::setEnableNextButton, throwable ->
                        Log.e(TAG, throwable.getMessage()));
        nameFieldListener.subscribe(name -> this.name = String.valueOf(name));
    }

    void clickNext() {
        rxPermissions.get().request(Manifest.permission.READ_PHONE_STATE)
                .subscribe(granted -> {
                    if (granted) getView().prepareParams();
                    else getView().finish();
                });
    }

    void registryNewUser(String imei) {
        Log.e(TAG, name);
        rxPermissions.get().request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(granted->registry(granted,imei));

    }
    private void registry(boolean granted,String imei){
        if (!granted) return;
        registryInteractor.regNewUser(name,imei)
                .subscribe(user -> getRouter().nextStep()
                        , e -> {
                            Log.e(TAG, e.getMessage());
                            if (e instanceof HttpException) {
                                ExceptionJSONInfo ex = ExceptionUtil.parseEx(((HttpException) e).response());
                                getView().showToast(ex.getMessage());
                            } else getView().showToast(R.string.toast_error_connect);
                        });
    }
}
