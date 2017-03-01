package com.welcome.studio.welcome.model.interactor;

import android.support.v7.widget.RecyclerView;

import com.kelvinapps.rxfirebase.RxFirebaseChildEvent;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.User;

import java.util.List;

import rx.Observable;

/**
 * Created by Royal on 11.02.2017. !
 */
public interface WallInteractor {
    Observable<Boolean> controlFab();

    Observable<List<Post>> controlPosts(RecyclerView recyclerView);

    Observable<RxFirebaseChildEvent<Post>> listenPosts(int limit);

    Observable<Boolean> incLikeCount(Post post);

    Observable<Boolean> decLikeCount(Post post);

    Observable<Boolean> incWillcomeCount(Post post);

    Observable<Boolean> decWillcomeCount(Post post);

    Observable<Boolean> incReportCount(Post post);

    Observable<Boolean> decReportCount(Post post);

    User getUserCache();
}
