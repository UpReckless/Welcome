package com.welcome.studio.welcome.model.interactor;


import com.welcome.studio.welcome.model.data.Rating;
import com.welcome.studio.welcome.model.data.User;

import rx.Observable;

public interface ProfileInteractor {
    User getUserCache();

    Observable<Rating> getRating();


}
