package com.welcome.studio.welcome.model.interactor;


import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.Rating;
import com.welcome.studio.welcome.model.data.User;

import java.util.List;

import rx.Observable;
import rx.Subscription;

public interface ProfileInteractor {
    User getUserCache();

    Observable<Rating> getRating(User user);

    Observable<List<Post>> getHistoryPosts(User user);

    Subscription listenPost(User user);

    Observable<List<Post>> getNowPosts(User user);

    Observable<List<Post>> getWillcomedPost(User user);

    Observable<Boolean> changeLikeCount(Post post);

    Observable<Boolean> changeWillcomeCount(Post post);

    Observable<Boolean> changeReportCount(Post post);
}
