package com.welcome.studio.welcome.ui.module.profile;

import android.net.Uri;

import com.welcome.studio.welcome.app.Injector;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.model.interactor.ProfileInteractor;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.module.main.MainRouter;

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
    private User user;

    @Inject
    ProfilePresenter(ProfileInteractor profileInteractor) {
        this.profileInteractor = profileInteractor;
    }

    @Override
    public void onStart() {
        getView().setData(user.getCity(), user.getRating());
        if (user.getPhotoPath() != null)
            getView().loadMainPhoto(new File(user.getPhotoPath()));
        else
            getView().loadMainPhoto(user.getPhotoRef() == null ? null : Uri.parse(user.getPhotoRef()));
        ratingSubscription = profileInteractor.getRating(user)
                .subscribe(rating1 -> {
                    getView().updateData(rating1);
                }, e -> {
                });
    }

    @Override
    public void onStop() {
        if (ratingSubscription != null)
            ratingSubscription.unsubscribe();
    }

    void destroy() {
        Injector.getInstance().clearProfileComponent();
    }

    public void setUser(User user) {
        this.user = user;
    }
}
