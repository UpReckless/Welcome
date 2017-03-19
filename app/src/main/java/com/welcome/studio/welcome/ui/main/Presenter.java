package com.welcome.studio.welcome.ui.main;

import android.graphics.Bitmap;
import android.util.Log;

import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.squareup.picasso.Picasso;
import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.model.interactor.MainInteractor;
import com.welcome.studio.welcome.model.data.User;

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
        view.setDrawer();
        router.navigateToWall();
        String photoPath = mainInteractor.getUserCache().getPhotoPath();
        if (photoPath != null) {
            view.loadProfileImage(((picasso, uri, exception) -> {
                view.loadProfileImage(R.mipmap.img_avatar);
                mainInteractor.downloadMyMainPhotoUri()
                        .subscribe(view::loadProfileImage, throwable -> {
                            Log.e(TAG, throwable.getMessage());
                        });

            }), photoPath);
        } else view.loadProfileImage(R.mipmap.img_avatar);

    }

    void onDrawerItemCLick(int position, IDrawerItem drawerItem) {
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


    void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        view.updateProfile(buildProfile(bitmap));
        if (from.equals(Picasso.LoadedFrom.NETWORK)) {
            mainInteractor.downloadMyMainPhotoBitmap(bitmap)
                    .subscribe();
        }
    }

    private IProfile buildProfile(Bitmap bitmap) {
        User user = mainInteractor.getUserCache();
        return new ProfileDrawerItem()
                .withIcon(bitmap)
                .withIdentifier(1)
                .withName(user.getNickname())
                .withEmail(user.getCity());
    }
}
