package com.welcome.studio.welcome.ui.module.profile.now;

import com.welcome.studio.welcome.app.RxBus;
import com.welcome.studio.welcome.model.data.Like;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.Report;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.model.data.Willcome;
import com.welcome.studio.welcome.model.entity.Author;
import com.welcome.studio.welcome.model.entity.PostEvent;
import com.welcome.studio.welcome.model.interactor.ProfileInteractor;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.module.main.MainRouter;
import com.welcome.studio.welcome.util.PostConverter;

import java.util.ArrayList;
import java.util.List;

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
    private Subscription willcomeListSubscription;
    private RxBus rxBus;
    private PostConverter postConverter;
    private User user;

    @Inject
    NowPresenter(ProfileInteractor profileInteractor, RxBus rxBus, PostConverter postConverter) {
        this.profileInteractor = profileInteractor;
        this.rxBus = rxBus;
        this.postConverter = postConverter;
    }

    @Override
    public void onStart() {
        if (busUserPostEventSubscription == null || busUserPostEventSubscription.isUnsubscribed())
            busUserPostEventSubscription = rxBus.getPostEvent().subscribe(this::busEventUserPost);
        if (nowPostsSubscription == null || nowPostsSubscription.isUnsubscribed())
            nowPostsSubscription = profileInteractor.getNowPosts(user)
                    .subscribe(posts -> {
                        postConverter.convertPostsToAdapter(posts, profileInteractor.getUserCache().getId())
                                .subscribeOn(Schedulers.computation())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(postList -> {
                                    getView().setPostList(posts);
                                    if (listenPostsSubscription == null || listenPostsSubscription.isUnsubscribed())
                                        listenPostsSubscription = profileInteractor.listenPost(user);
                                });
                    });
        if (willcomeListSubscription == null || willcomeListSubscription.isUnsubscribed())
            willcomeListSubscription = profileInteractor.getWillcomedPost(user).subscribe(posts -> {
                getView().setWillcomeList(posts);
            });

    }

    @Override
    public void onStop() {
        if (willcomeListSubscription!=null)
            willcomeListSubscription.unsubscribe();
    }

    void destroy() {
        if (listenPostsSubscription != null)
            listenPostsSubscription.unsubscribe();
        if (busUserPostEventSubscription != null)
            busUserPostEventSubscription.unsubscribe();
        if (nowPostsSubscription != null)
            nowPostsSubscription.isUnsubscribed();
    }


    void willcomedPostClicked(Post post) {
        getRouter().navigateToPostWatcher(post, true);
    }

    public void likeClicked(Post post) {
        profileInteractor.changeLikeCount(post)
                .subscribe(success -> {
                    if (!success) getView().showToast("Internet connection failed");
                });
    }


    void willcomeClicked(Post post) {
        //do nothing
    }

    void reportClicked(Post post) {
        profileInteractor.changeReportCount(post)
                .subscribe(success -> {
                    if (!success) getView().showToast("Internet connection failed");
                });
    }

    void commentClicked(Post post) {
        getRouter().navigateToComment(post);
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
                postConverter.convertPostToAdapter(postEvent.getPost(), profileInteractor.getUserCache().getId())
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(getView()::changePost);
                break;
            case REMOVED:
                getView().removePost(postEvent.getPost());
                break;
        }
    }

    public void create() {
        getView().initAdapters(profileInteractor.getUserCache());
    }
    public void setUser(User user){
        this.user=user;
    }

    void userProfileClicked(Post post) {
        getRouter().navigateToProfile(profileInteractor.getUserCache(),false);
    }

    void likeCountClicked(Post post) {
        if (post.getLikes() != null) {
            List<Author> authors = new ArrayList<>(post.getLikes().size());
            for (Like like :
                    post.getLikes().values())
                authors.add(like.getAuthor());
            getRouter().navigateToAuthorWatcher(authors);
        }
    }

    void willcomeCountClicked(Post post) {
        if (post.getWillcomes() != null) {
            List<Author> authors = new ArrayList<>(post.getWillcomes().size());
            for (Willcome willcome :
                    post.getWillcomes().values())
                authors.add(willcome.getAuthor());
            getRouter().navigateToAuthorWatcher(authors);
        }
    }

    void reportCountClicked(Post post) {
        if (post.getReports() != null) {
            List<Author> authors = new ArrayList<>(post.getReports().size());
            for (Report report :
                    post.getReports().values())
                authors.add(report.getAuthor());
            getRouter().navigateToAuthorWatcher(authors);
        }
    }

    void addressLineClicked(Post post) {

    }
}
