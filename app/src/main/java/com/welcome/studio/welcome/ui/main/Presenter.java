package com.welcome.studio.welcome.ui.main;

import com.welcome.studio.welcome.model.interactor.MainInteractor;

import javax.inject.Inject;

public class Presenter {
    private static final String TAG = "ui.main.PresenterImpl";
    private View view;
    private MainRouter router;
    private MainInteractor mainInteractor;

    @Inject
    Presenter(View view, MainInteractor mainInteractor) {
        this.view = view;
        this.mainInteractor = mainInteractor;
    }

    public void create() {
        mainInteractor.isFirstStart()
                .subscribe(isFirst -> {
                    if (isFirst)
                        router.navigateToRegistry();
                    else mainInteractor.auth()
                            .subscribe(success -> {
                                start();
                            });
                });
    }

    void setRouter(MainRouter router) {
        this.router = router;
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
                throw new RuntimeException("Test error handling exception DONT REPORT THIS");

        }
    }

    void onProfileClick() {
        router.navigateToProfile(mainInteractor.getUserCache());
    }

}
