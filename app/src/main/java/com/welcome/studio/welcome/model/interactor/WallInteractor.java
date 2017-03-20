package com.welcome.studio.welcome.model.interactor;

import android.support.v7.widget.RecyclerView;

import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.User;

import rx.Observable;
import rx.Subscription;

/**
 * Created by Royal on 11.02.2017. !
 */
public interface WallInteractor {
    Observable<Boolean> controlFab();

    Subscription controlPosts(RecyclerView recyclerView);

    Subscription listenPosts();

    Observable<Boolean> sharePost(Post post);

    Observable<Boolean> changeLikeCount(Post post);

    Observable<Boolean> changeWillcomeCount(Post post);

    Observable<Boolean> changeReportCount(Post post);

    User getUserCache();
}
