package com.welcome.studio.welcome.ui.wall;

import android.Manifest;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.welcome.studio.welcome.app.Injector;
import com.welcome.studio.welcome.app.RxBus;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.model.entity.PostEvent;
import com.welcome.studio.welcome.model.interactor.MainInteractor;
import com.welcome.studio.welcome.model.interactor.WallInteractor;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.main.MainRouter;
import com.welcome.studio.welcome.util.PostConverter;

import java.util.List;

import javax.inject.Inject;

import dagger.Lazy;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by @mistreckless on 18.01.2017. !
 */

class WallPresenter extends BasePresenter<WallView, MainRouter> {
    private WallInteractor wallInteractor;
    private MainInteractor mainInteractor;
    private Lazy<RxPermissions> rxPermissions;
    private PostConverter postConverter;
    private RxBus bus;
    private Subscription pagingSubscription;
    private Subscription listenSubscription;
    private Subscription controlFabSubscription;
    private Subscription busPostListSubscription;
    private Subscription busPostEventSubscription;
    private Subscription busUserPostSubscription;
    private User user;
    private boolean isSharing;

    @Inject
    WallPresenter(WallInteractor wallInteractor, MainInteractor mainInteractor,
                  Lazy<RxPermissions> rxPermissions, RxBus bus, PostConverter postConverter) {
        this.wallInteractor = wallInteractor;
        this.mainInteractor = mainInteractor;
        this.rxPermissions = rxPermissions;
        this.bus = bus;
        this.postConverter = postConverter;
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
        if (pagingSubscription == null)
            getView().showProgressBar(true);
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
                                    .subscribe(postList -> {
                                        if (postList.size() > 0)
                                            postConverter.convertPostsToAdapter(postList, user.getId())
                                                    .subscribe(posts -> {
                                                        getView().showProgressBar(false);
                                                        getView().addPosts(posts);
                                                        getView().refreshPosts(recyclerView.getAdapter().getItemCount() - posts.size());
                                                    });
                                        else getView().showProgressBar(false);
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

    void destroy() {
        if (busPostListSubscription != null)
            busPostListSubscription.unsubscribe();
        if (pagingSubscription != null)
            pagingSubscription.unsubscribe();
        if (busPostEventSubscription != null)
            busPostEventSubscription.unsubscribe();
        if (busUserPostSubscription != null)
            busUserPostSubscription.unsubscribe();
        if (listenSubscription != null)
            listenSubscription.unsubscribe();
        Injector.getInstance().clearWallComponent();
    }

    void commentClicked(Post post) {
        getRouter().navigateToComment(post);
    }

    void refreshAll() {
        if (!isSharing)
            getRouter().navigateToWall();
    }

    private void busEventUserPost(Post post) {
        isSharing = true;
        getView().setUserPost(post);
        mainInteractor.checkServerConnection().subscribe(success -> {
            if (success) {
                wallInteractor.sharePost(post)
                        .subscribe(success1 -> {
                            isSharing = false;
                            if (!success1) {
                                Log.e("userPost", "failed");
                                post.setTryToUpload(true);
                                getView().setUserPost(post);
                            }
                        }, throwable -> {
                            Log.e("userPostEr", "faild", throwable);
                            isSharing = false;
                            post.setTryToUpload(true);
                            getView().setUserPost(post);
                        });
            } else {
                post.setTryToUpload(true);
                getView().setUserPost(post);
            }
        });

    }

    private void busEventPostList(List<Post> posts, RecyclerView recyclerView) {
        if (getView() != null) {
            getView().showProgressBar(false);
            postConverter.convertPostsToAdapter(posts, user.getId())
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
                    postConverter.convertPostToAdapter(postEvent.getPost(), user.getId())
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
}
