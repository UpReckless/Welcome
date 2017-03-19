package com.welcome.studio.welcome.ui.profile;

import com.welcome.studio.welcome.model.interactor.ProfileInteractor;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.main.MainRouter;

import java.io.File;

import javax.inject.Inject;

/**
 * Created by Royal on 15.01.2017.
 */

public class ProfilePresenter extends BasePresenter<ProfileView, MainRouter> {
    private static final String TAG="ProfilePresenter";
    private ProfileInteractor profileInteractor;

    @Inject
    ProfilePresenter(ProfileInteractor profileInteractor){
        this.profileInteractor=profileInteractor;
    }

    @Override
    public void onStart() {
        User user=profileInteractor.getUserCache();
        getView().setData(user.getCity(),user.getRating());
        getView().loadMainPhoto(user.getPhotoPath()!=null? new File(user.getPhotoPath()):null);
        profileInteractor.getRating()
                .subscribe(rating1 -> {
                    getView().updateData(rating1);
                },e->{});
    }

    @Override
    public void onStop() {

    }

}
