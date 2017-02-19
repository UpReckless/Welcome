package com.welcome.studio.welcome.model.interactor;

import com.welcome.studio.welcome.model.data.Rating;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.model.repository.UserRepository;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Royal on 11.02.2017. !
 */

public class ProfileInteractorImpl implements ProfileInteractor {
    private UserRepository userRepository;

    @Inject
    public ProfileInteractorImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public User getUserCache() {
        return userRepository.getUserCache();
    }

    @Override
    public Observable<Rating> getRating() {
        long id=getUserCache().getId();
        return userRepository.getRating(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
