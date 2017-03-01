package com.welcome.studio.welcome.ui.wall;

import android.Manifest;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.kelvinapps.rxfirebase.RxFirebaseChildEvent;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.welcome.studio.welcome.model.data.Like;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.Report;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.model.data.Willcome;
import com.welcome.studio.welcome.model.interactor.WallInteractor;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.main.MainRouter;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.Lazy;
import rx.Subscription;

/**
 * Created by @mistreckless on 18.01.2017. !
 */

class WallPresenter extends BasePresenter<WallView, MainRouter> {
    private WallInteractor wallInteractor;
    private Lazy<RxPermissions> rxPermissions;
    private Subscription pagingSubscription;
    private int limit;
    private Subscription listenSubscription;

    @Inject
    WallPresenter(WallInteractor wallInteractor, Lazy<RxPermissions> rxPermissions) {
        this.wallInteractor = wallInteractor;
        this.rxPermissions = rxPermissions;
    }

    @Override
    public void onStart() {
        wallInteractor.controlFab()
                .subscribe(getView()::setFabEnabled);
        if (listenSubscription != null && listenSubscription.isUnsubscribed())
            listenSubscription = wallInteractor.listenPosts(limit)
                    .subscribe(this::realTimeProvider);

    }

    @Override
    public void onStop() {
        if (pagingSubscription != null)
            pagingSubscription.unsubscribe();
        if (listenSubscription != null)
            listenSubscription.unsubscribe();
    }

    void onFabClick() {
        rxPermissions.get().request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted)
                        getRouter().navigateToPhoto();
                });
    }


    void controlPaging(RecyclerView recyclerView) {
        pagingSubscription = wallInteractor.controlPosts(recyclerView)
                .subscribe(postList -> {
                    List<Post> posts = convertPostsToAdapter(postList);

                    getView().addPosts(posts);
                    getView().refreshPosts(recyclerView.getAdapter().getItemCount() - posts.size());

                    limit += posts.size();
                    if (posts.size() > 0) {
                        if (listenSubscription != null) listenSubscription.unsubscribe();
                        listenSubscription = wallInteractor.listenPosts(limit)
                                .subscribe(this::realTimeProvider);
                    }
                });

    }

    void likeClicked(Post post, int position) {
        if (post.isLiked())
            wallInteractor.decLikeCount(post)
                    .subscribe(success -> {
                    });
        else
            wallInteractor.incLikeCount(post)
                    .subscribe(updLike -> {
                    });
        post.setLiked(!post.isLiked());
        getView().updatePostView(post, position);
    }

    void willcomeClicked(Post post, int position) {
        if (post.isWillcomed())
            wallInteractor.decWillcomeCount(post)
                    .subscribe(success -> {
                    });
        else wallInteractor.incWillcomeCount(post)
                .subscribe(success -> {
                });
        post.setWillcomed(!post.isWillcomed());
        getView().updatePostView(post, position);
    }

    void reportClicked(Post post, int position) {
        if (post.isReported())
            wallInteractor.decReportCount(post)
                    .subscribe(success -> {
                    });
        else wallInteractor.incReportCount(post)
                .subscribe(success -> {
                });
        post.setReported(!post.isReported());
        getView().updatePostView(post, position);
    }

    void likeCountCLicked() {

    }

    private void realTimeProvider(RxFirebaseChildEvent<Post> postRxFirebaseEvent) {
        switch (postRxFirebaseEvent.getEventType()) {
            case ADDED:
                //do nothing
                Log.e("Added", postRxFirebaseEvent.getKey());
                break;
            case REMOVED:
                //remove
                getView().removePost(postRxFirebaseEvent.getValue());
                Log.e("Removed", postRxFirebaseEvent.getKey());
                break;
            case CHANGED:
                //update
                Log.e("Changed", postRxFirebaseEvent.getKey());
                getView().updatePostEvent(postRxFirebaseEvent.getValue());
                break;
            case MOVED:
                //hz poka 4to do nothing
                Log.e("Moved", postRxFirebaseEvent.getKey());
                break;
            default:
                throw new RuntimeException("unexpected event type " + postRxFirebaseEvent.getEventType().name());
        }
    }

    private List<Post> convertPostsToAdapter(List<Post> posts) {
        User user = wallInteractor.getUserCache();
        for (int i = 0; i < posts.size(); i++) {
            Post post = posts.get(i);
            if (post.getLikes() != null)
                for (Like like :
                        post.getLikes().values()) {
                    if (like.getAuthor().getuId() == user.getId())
                        post.setLiked(true);
                }
            if (post.getWillcomes() != null)
                for (Willcome willcome :
                        post.getWillcomes().values()) {
                    if (willcome.getAuthor().getuId() == user.getId())
                        post.setWillcomed(true);
                }
            if (post.getReports() != null)
                for (Report report : post.getReports().values()) {
                    if (report.getAuthor().getuId() == user.getId())
                        post.setReported(true);
                }
            posts.set(i,post);
        }
        Collections.reverse(posts);
        return posts;
    }

    void destroy() {
        listenSubscription = null;
        pagingSubscription = null;
    }

    void commentClicked(Post post) {
        getRouter().navigateToComment(post);
    }
}
