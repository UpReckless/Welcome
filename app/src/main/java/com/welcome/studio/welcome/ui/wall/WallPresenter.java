package com.welcome.studio.welcome.ui.wall;

import android.Manifest;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.welcome.studio.welcome.app.Injector;
import com.welcome.studio.welcome.app.RxBus;
import com.welcome.studio.welcome.model.data.Like;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.Report;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.model.data.Willcome;
import com.welcome.studio.welcome.model.entity.PostEvent;
import com.welcome.studio.welcome.model.interactor.MainInteractor;
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
    private MainInteractor mainInteractor;
    private Lazy<RxPermissions> rxPermissions;
    private RxBus bus;
    private Subscription pagingSubscription;
    private Subscription listenSubscription;
    private Subscription controlFabSubscription;
    private Subscription busPostListSubscription;
    private Subscription busPostEventSubscription;
    private Subscription busUserPostSubscription;
    private User user;

    @Inject
    WallPresenter(WallInteractor wallInteractor, MainInteractor mainInteractor, Lazy<RxPermissions> rxPermissions, RxBus bus) {
        this.wallInteractor = wallInteractor;
        this.mainInteractor = mainInteractor;
        this.rxPermissions = rxPermissions;
        this.bus = bus;
        user = this.wallInteractor.getUserCache();
    }


    @Override
    public void onStart() {
        controlFabSubscription = wallInteractor.controlFab()
                .subscribe(getView()::setFabEnabled);
        if (busUserPostSubscription == null || busUserPostSubscription.isUnsubscribed())
            busUserPostSubscription = bus.getUserPostEvent().subscribe(this::busEventUserPost);
        if (busPostEventSubscription == null || busPostEventSubscription.isUnsubscribed())
            busPostEventSubscription = bus.getPostEvent().subscribe(this::busEventPostEvent);
    }

    @Override
    public void onStop() {
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


    void controlPaging(RecyclerView recyclerView) {
        mainInteractor.checkServerConnection()
                .subscribe(success -> {
                    if (success) {
                        if (pagingSubscription == null || pagingSubscription.isUnsubscribed())
                            pagingSubscription = wallInteractor.controlPosts(recyclerView);
                        if (busPostListSubscription == null || busPostListSubscription.isUnsubscribed())
                            busPostListSubscription = bus.getPostList().
                                    subscribe(posts -> {
                                        busEventPostList(posts, recyclerView);
                                    });
                    } else {
                        if (pagingSubscription == null)
                            pagingSubscription = wallInteractor.getCachedPosts()
                                    .subscribe(posts -> {
                                        Collections.reverse(posts);
                                        getView().addPosts(posts);
                                        getView().refreshPosts(recyclerView.getAdapter().getItemCount() - posts.size());
                                    });
                    }
                });

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

    private void busEventUserPost(Post post) {
        getView().setUserPost(post);
        wallInteractor.sharePost(post)
                .subscribe(success -> {
                    if (!success) Log.e("userPost", "failed");
                }, throwable -> Log.e("userPostEr", "faild", throwable));
    }

    private void busEventPostList(List<Post> posts, RecyclerView recyclerView) {
        if (getView() != null) {
            convertPostsToAdapter(posts, user.getId())
                    .subscribe(posts1 -> {
                        getView().addPosts(posts1);
                        getView().refreshPosts(recyclerView.getAdapter().getItemCount() - posts1.size());
                    });
            if (listenSubscription == null || listenSubscription.isUnsubscribed())
                listenSubscription = wallInteractor.listenPosts();

        }
    }

    private void busEventPostEvent(PostEvent postEvent) {
        if (getView() != null) {
            switch (postEvent.getEventType()) {
                case ADDED:
                    //only works for user posts
                    Log.e("Added", postEvent.getPost().getId());
                    getView().updateUserPost(postEvent.getPost());
                    break;
                case REMOVED:
                    //remove
                    getView().removePost(postEvent.getPost());
                    Log.e("Removed", postEvent.getPost().getId());
                    break;
                case CHANGED:
                    //update
                    Log.e("Changed", postEvent.getPost().getId());
                    convertPostToAdapter(postEvent.getPost(), user.getId())
                            .subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(getView()::updatePost, throwable -> Log.e("ChangedErr", throwable.getMessage()));
                    break;
                case MOVED:
                    //do nothing
                    Log.e("Moved", postEvent.getPost().getId());
                    break;
            }
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
        if (busPostListSubscription != null)
            busPostListSubscription.unsubscribe();
        if (pagingSubscription != null)
            pagingSubscription.unsubscribe();
        if (busPostEventSubscription != null)
            busPostEventSubscription.unsubscribe();
        if (listenSubscription != null)
            listenSubscription.unsubscribe();
        Injector.getInstance().clearWallComponent();
    }

    void commentClicked(Post post) {
        getRouter().navigateToComment(post);
    }
}
