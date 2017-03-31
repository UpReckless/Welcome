package com.welcome.studio.welcome.model.interactor;


import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.Rating;
import com.welcome.studio.welcome.model.data.User;

import java.util.List;

import rx.Observable;
import rx.Subscription;

public interface ProfileInteractor {
    User getUserCache();

    Observable<Rating> getRating();

    Observable<List<Post>> getHistoryPosts();

    Subscription listenPost();

    Observable<List<Post>> getNowPosts();
}
