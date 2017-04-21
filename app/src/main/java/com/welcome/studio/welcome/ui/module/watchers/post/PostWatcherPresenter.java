package com.welcome.studio.welcome.ui.module.watchers.post;

import android.util.Log;

import com.welcome.studio.welcome.app.Injector;
import com.welcome.studio.welcome.model.data.Like;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.Report;
import com.welcome.studio.welcome.model.data.Willcome;
import com.welcome.studio.welcome.model.entity.Author;
import com.welcome.studio.welcome.model.interactor.MainInteractor;
import com.welcome.studio.welcome.model.interactor.PostWatcherInteractor;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.module.main.MainRouter;
import com.welcome.studio.welcome.util.PostConverter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by @mistreckless on 31.03.2017. !
 */

public class PostWatcherPresenter extends BasePresenter<PostWatcherView, MainRouter> {
    private Post post;
    private boolean isRealTime;
    private PostWatcherInteractor postWatcherInteractor;
    private Subscription listenPostSubscription;
    private PostConverter postConverter;
    private MainInteractor mainInteractor;

    @Inject
    PostWatcherPresenter(PostWatcherInteractor postWatcherInteractor, MainInteractor mainInteractor, PostConverter postConverter) {
        this.postWatcherInteractor = postWatcherInteractor;
        this.mainInteractor = mainInteractor;
        this.postConverter = postConverter;
    }

    @Override
    public void onStart() {
        getView().initUi(post, postWatcherInteractor.getUserCache());
        if (isRealTime && (listenPostSubscription == null || listenPostSubscription.isUnsubscribed()))
            listenPostSubscription = postWatcherInteractor.listenPost(post)
                    .subscribe(this::realTimeProvider, throwable -> {
                        Log.e("WATCHER", throwable.getMessage());
                    });
    }

    @Override
    public void onStop() {
        if (listenPostSubscription != null)
            listenPostSubscription.unsubscribe();
    }

    void setVariables(Post post, boolean isRealTime) {
        this.post = post;
        this.isRealTime = isRealTime;
    }

    void commentClicked() {
        getRouter().navigateToComment(post);
    }

    void destroy() {
        Injector.getInstance().clearWatcherComponent();
    }

    void likeClicked() {
        if (isRealTime)
            postWatcherInteractor.changeLikeCount(post)
                    .subscribe(success -> {
                        if (!success) getView().showToast("Network connection failed");
                    });
    }

    void willcomeClicked() {
        if (isRealTime)
            postWatcherInteractor.changeWillcomeCount(post)
                    .subscribe(success -> {
                        if (!success) getView().showToast("Network connection failed");
                    });
    }

    void reportClicked() {
        if (isRealTime)
            postWatcherInteractor.changeReportCount(post)
                    .subscribe(success -> {
                        if (!success) getView().showToast("Network connection failed");
                    });
    }


    void likeCountClicked() {
        if (post.getLikes() != null) {
            List<Author> authors = new ArrayList<>(post.getLikes().size());
            for (Like like :
                    post.getLikes().values())
                authors.add(like.getAuthor());
            getRouter().navigateToAuthorWatcher(authors);
        }
    }

    void willcomeCountClicked() {
        if (post.getWillcomes() != null) {
            List<Author> authors = new ArrayList<>(post.getWillcomes().size());
            for (Willcome willcome :
                    post.getWillcomes().values())
                authors.add(willcome.getAuthor());
            getRouter().navigateToAuthorWatcher(authors);
        }
    }

    void reportCountClicked() {
        if (post.getReports() != null) {
            List<Author> authors = new ArrayList<>(post.getReports().size());
            for (Report report :
                    post.getReports().values())
                authors.add(report.getAuthor());
            getRouter().navigateToAuthorWatcher(authors);
        }
    }

    void profileClicked() {
        mainInteractor.getUser(post.getAuthor())
                .subscribe(user -> getRouter().navigateToProfile(user, user.getId() != mainInteractor.getUserCache().getId()));
    }

    private void realTimeProvider(Post post) {
        Log.e("WATHCER", post.getDescription() + post.getAuthor().getName());
        postConverter.convertPostToAdapter(post, postWatcherInteractor.getUserCache().getId())
                .doOnNext(post1 -> this.post = post1)
                .subscribe(post1 -> getView().updatePost(post1, postWatcherInteractor.getUserCache()));
    }


}
