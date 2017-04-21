package com.welcome.studio.welcome.ui.module.main;

import android.Manifest;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.model.interactor.MainInteractor;
import com.welcome.studio.welcome.util.Constance;

import javax.inject.Inject;

import dagger.Lazy;

public class Presenter {
    private static final String TAG = "ui.main.PresenterImpl";
    private View view;
    private MainRouter router;
    private MainInteractor mainInteractor;
    private Lazy<RxPermissions> rxPermissions;

    @Inject
    Presenter(View view, MainInteractor mainInteractor, Lazy<RxPermissions> rxPermissions) {
        this.view = view;
        this.mainInteractor = mainInteractor;
        this.rxPermissions=rxPermissions;
    }

    public void create(int notificationCode) {
        User user = mainInteractor.getUserCache();
        switch (notificationCode) {
            case Constance.IntentCodeHolder.NOTIFICATION_WILLCOME_CODE:
                view.setDrawer(user);
                router.navigateToProfile(user,false);
                break;
            default:
                mainInteractor.isFirstStart()
                        .subscribe(isFirst -> {
                            if (isFirst)
                                router.navigateToRegistry();
                            else mainInteractor.auth()
                                    .subscribe(success -> {
                                        start();
                                    });
                        });
                break;
        }
        rxPermissions.get().request(Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.ACCESS_NOTIFICATION_POLICY).subscribe();
    }

    void setRouter(MainRouter router) {
        this.router = router;
    }

    void setView(View view) {
        this.view = view;
    }

    void start() {
        view.setDrawer(mainInteractor.getUserCache());
        router.navigateToWall();
    }

    void onDrawerItemCLick(int position) {
        switch (position) {
            case 1:
                router.navigateToWall();
                break;
            case 2:
               // throw new RuntimeException("Test error handling exception DONT REPORT THIS");
                break;
            case 5:
                router.navigateToSearch();
                break;
        }
    }

    void onProfileClick() {
        router.navigateToProfile(mainInteractor.getUserCache(),false);
    }

}
