package com.welcome.studio.welcome.ui.profile;

import com.welcome.studio.welcome.app.Injector;
import com.welcome.studio.welcome.model.interactor.ProfileInteractor;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.main.MainRouter;

import java.io.File;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by @mistreckless on 15.01.2017. !
 */

public class ProfilePresenter extends BasePresenter<ProfileView, MainRouter> {
    private static final String TAG = "ProfilePresenter";
    private ProfileInteractor profileInteractor;
    private Subscription ratingSubscription;

    @Inject
    ProfilePresenter(ProfileInteractor profileInteractor) {
        this.profileInteractor = profileInteractor;
    }

    @Override
    public void onStart() {
        User user = profileInteractor.getUserCache();
        getView().setData(user.getCity(), user.getRating());
        getView().loadMainPhoto(user.getPhotoPath() != null ? new File(user.getPhotoPath()) : null);
        ratingSubscription = profileInteractor.getRating()
                .subscribe(rating1 -> {
                    getView().updateData(rating1);
                }, e -> {
                });
    }

    @Override
    public void onStop() {
        if (ratingSubscription!=null)
            ratingSubscription.unsubscribe();
    }

    void destroy() {
        Injector.getInstance().clearProfileComponent();
    }
}
