package com.welcome.studio.welcome.ui.profile.today;

import com.welcome.studio.welcome.app.RxBus;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.model.entity.PostEvent;
import com.welcome.studio.welcome.model.interactor.ProfileInteractor;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.main.MainRouter;
import com.welcome.studio.welcome.util.PostConverter;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Royal on 11.02.2017. !
 */

class NowPresenter extends BasePresenter<NowView, MainRouter> {
    private ProfileInteractor profileInteractor;
    private Subscription listenPostsSubscription;
    private Subscription busUserPostEventSubscription;
    private Subscription nowPostsSubscription;
    private RxBus rxBus;
    private PostConverter postConverter;
    private User user;

    @Inject
    NowPresenter(ProfileInteractor profileInteractor, RxBus rxBus, PostConverter postConverter) {
        this.profileInteractor = profileInteractor;
        this.rxBus = rxBus;
        this.postConverter = postConverter;
        user = profileInteractor.getUserCache();
    }

    @Override
    public void onStart() {
        if (busUserPostEventSubscription == null || busUserPostEventSubscription.isUnsubscribed())
            busUserPostEventSubscription = rxBus.getPostEvent().subscribe(this::busEventUserPost);
        if (nowPostsSubscription == null || nowPostsSubscription.isUnsubscribed())
            nowPostsSubscription = profileInteractor.getNowPosts()
                    .subscribe(posts -> {
                        postConverter.convertPostsToAdapter(posts, user.getId())
                                .subscribeOn(Schedulers.computation())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(postList -> {
                                    getView().setPostList(posts);
                                    if (listenPostsSubscription == null || listenPostsSubscription.isUnsubscribed())
                                        listenPostsSubscription = profileInteractor.listenPost();
                                });
                    });

    }

    @Override
    public void onStop() {

    }

    void destroy() {
        if (listenPostsSubscription != null)
            listenPostsSubscription.unsubscribe();
        if (busUserPostEventSubscription != null)
            busUserPostEventSubscription.unsubscribe();
        if (nowPostsSubscription != null)
            nowPostsSubscription.isUnsubscribed();
    }

    private void busEventUserPost(PostEvent postEvent) {
        if (getView() == null) return;
        switch (postEvent.getEventType()) {
            case ADDED:
                //do nothing
                break;
            case MOVED:
                //no idea
                break;
            case CHANGED:
                postConverter.convertPostToAdapter(postEvent.getPost(), user.getId())
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(getView()::changePost);
                break;
            case REMOVED:
                getView().removePost(postEvent.getPost());
                break;
        }
    }
}
