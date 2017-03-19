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
import java.util.Map;

import javax.inject.Inject;

import dagger.Lazy;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.welcome.studio.welcome.util.Constance.ConstHolder.MAX_POST_LIMIT;

/**
 * Created by @mistreckless on 18.01.2017. !
 */

class WallPresenter extends BasePresenter<WallView, MainRouter> {
    private WallInteractor wallInteractor;
    private Lazy<RxPermissions> rxPermissions;
    private Subscription pagingSubscription;
    private Subscription listenSubscription;
    private Subscription controlFabSubscription;

    @Inject
    WallPresenter(WallInteractor wallInteractor, Lazy<RxPermissions> rxPermissions) {
        this.wallInteractor = wallInteractor;
        this.rxPermissions = rxPermissions;
    }

    @Override
    public void onStart() {
        controlFabSubscription = wallInteractor.controlFab()
                .subscribe(getView()::setFabEnabled);
        if (listenSubscription != null && listenSubscription.isUnsubscribed())
            listenSubscription = wallInteractor.listenPosts()
                    .subscribe(this::realTimeProvider);

    }

    @Override
    public void onStop() {
        if (pagingSubscription != null)
            pagingSubscription.unsubscribe();
        if (listenSubscription != null)
            listenSubscription.unsubscribe();
        if (controlFabSubscription != null)
            controlFabSubscription.unsubscribe();
    }

    void onFabClick() {
        rxPermissions.get().request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted)
                        getRouter().navigateToPhoto();
                });
    }


    void controlPaging(RecyclerView recyclerView, Post post) {
        pagingSubscription = wallInteractor.controlPosts(recyclerView)
                .subscribe(postList -> {
                    Collections.reverse(postList);
                    User user = wallInteractor.getUserCache();
                    convertPostsToAdapter(postList, user.getId())
                            .subscribe(posts -> {
                                getView().addPosts(posts);
                                getView().refreshPosts(recyclerView.getAdapter().getItemCount() - posts.size());
                                if (listenSubscription != null)
                                    listenSubscription.unsubscribe();
                                listenSubscription = wallInteractor.listenPosts()
                                        .subscribe(this::realTimeProvider);
                            }, throwable -> {
                                Log.e("ControlPagin", throwable.getMessage());
                            });
                });
        if (post != null) {
            getView().setUserPost(post);
            wallInteractor.sharePost(post)
                    .subscribe(success -> {
                        Log.e("postShare", String.valueOf(success));
                    }, throwable -> {
                        Log.e("SharePost", "Unsuccessfully upload");
                    });
        }

    }

    void likeClicked(Post post) {
        wallInteractor.changeLikeCount(post)
                .subscribe(success -> {
                    if (!success) getView().showToast("Internet connection failed");
                });
    }

    void willcomeClicked(Post post) {
        wallInteractor.changeWillcomeCount(post)
                .subscribe(success -> {
                    if (!success) getView().showToast("Internet connection failed");
                });

    }

    void reportClicked(Post post) {
        wallInteractor.changeReportCount(post)
                .subscribe(success -> {
                    if (!success) getView().showToast("Internet connection failed");
                });
    }

    void likeCountCLicked() {

    }

    private void realTimeProvider(RxFirebaseChildEvent<Post> postRxFirebaseEvent) {
        User user = wallInteractor.getUserCache();
        switch (postRxFirebaseEvent.getEventType()) {
            case ADDED:
                //handle user post
                Log.e("Added", postRxFirebaseEvent.getKey());
                if (user.getId() == postRxFirebaseEvent.getValue().getAuthor().getuId())
                    getView().updateUserPost(postRxFirebaseEvent.getValue());
                break;
            case REMOVED:
                //remove
                getView().removePost(postRxFirebaseEvent.getValue());
                Log.e("Removed", postRxFirebaseEvent.getKey());
                break;
            case CHANGED:
                //update
                Log.e("Changed", postRxFirebaseEvent.getKey());
                convertPostToAdapter(postRxFirebaseEvent.getValue(), user.getId())
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(getView()::updatePost, throwable -> Log.e("ChangedErr", throwable.getMessage()));
                break;
            case MOVED:
                //hz poka 4to do nothing
                Log.e("Moved", postRxFirebaseEvent.getKey());
                break;
            default:
                throw new RuntimeException("unexpected event type " + postRxFirebaseEvent.getEventType().name());
        }
    }

    private Observable<List<Post>> convertPostsToAdapter(List<Post> posts, long uId) {
        return Observable.from(posts)
                .flatMap(post -> convertPostToAdapter(post, uId))
                .buffer(MAX_POST_LIMIT)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());

    }

    private Observable<Post> convertPostToAdapter(Post post, long uId) {
        return Observable.zip(likeFilter(post.getLikes(), uId), willcomeFilter(post.getWillcomes(), uId), reportFilter(post.getReports(), uId),
                (isLiked, isWillcomed, isReported) -> {
                    post.setLiked(isLiked);
                    post.setWillcomed(isWillcomed);
                    post.setReported(isReported);
                    return post;
                });
    }

    private Observable<Boolean> likeFilter(Map<String, Like> likes, long uId) {
        return Observable.just(likes)
                .map(likes1 -> {
                    if (likes1 != null)
                        for (Like like :
                                likes1.values())
                            if (like.getAuthor().getuId() == uId)
                                return true;

                    return false;
                });
    }

    private Observable<Boolean> willcomeFilter(Map<String, Willcome> willcomeMap, long uId) {
        return Observable.just(willcomeMap)
                .map(willcomes -> {
                    if (willcomes != null)
                        for (Willcome willcome :
                                willcomes.values())
                            if (willcome.getAuthor().getuId() == uId)
                                return true;
                    return false;
                });
    }

    private Observable<Boolean> reportFilter(Map<String, Report> reportMap, long uId) {
        return Observable.just(reportMap)
                .map(reports -> {
                    if (reports != null)
                        for (Report report :
                                reports.values())
                            if (report.getAuthor().getuId() == uId)
                                return true;
                    return false;
                });
    }

    void destroy() {
        listenSubscription = null;
        pagingSubscription = null;
        controlFabSubscription = null;
    }

    void commentClicked(Post post) {
        getRouter().navigateToComment(post);
    }
}
