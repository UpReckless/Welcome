package com.welcome.studio.welcome.ui.wall;

import android.Manifest;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.welcome.studio.welcome.model.interactor.WallInteractor;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.main.MainRouter;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * Created by @mistreckless on 18.01.2017. !
 */

class WallPresenter extends BasePresenter<WallView, MainRouter> {
    private WallInteractor wallInteractor;
    private Lazy<RxPermissions> rxPermissions;

    @Inject
    WallPresenter(WallInteractor wallInteractor, Lazy<RxPermissions> rxPermissions) {
        this.wallInteractor = wallInteractor;
        this.rxPermissions = rxPermissions;
    }

    @Override
    public void onStart() {
        wallInteractor.controlFab()
                .subscribe(enabled -> {
                    getView().setFabEnabled(enabled);
                });
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference testRf = database.getReference("posts");
//        RxFirebaseDatabase.observeSingleValueEvent(testRf, DataSnapshotMapper.listOf(Post.class)).subscribe(posts -> {
//            if (posts != null && posts.size() > 0)
//                getView().updateUi(posts);
//        });
    }

    @Override
    public void onStop() {

    }

    void onFabClick() {
        rxPermissions.get().request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted)
                        getRouter().navigateToPhoto();
                });
    }
}
