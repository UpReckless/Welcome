package com.welcome.studio.welcome.ui.module.profile.history;

import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.model.interactor.ProfileInteractor;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.module.main.MainRouter;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by @mistreckless on 16.01.2017. !
 */

public class HistoryPresenter extends BasePresenter<HistoryView,MainRouter> {
    private ProfileInteractor profileInteractor;
    private Subscription postsSubscription;
    private User user;

    @Inject
    HistoryPresenter(ProfileInteractor profileInteractor){
        this.profileInteractor=profileInteractor;
    }

    @Override
    public void onStart() {
        if (postsSubscription==null || postsSubscription.isUnsubscribed())
            postsSubscription=profileInteractor.getHistoryPosts(user)
                    .subscribe(getView()::setPostsToAdapter,throwable -> {});
    }

    @Override
    public void onStop() {

    }

    public void setUser(User user){
        this.user=user;
    }

    void postClicked(Post post) {
        getRouter().navigateToPostWatcher(post, false);
    }

    void destroy() {
        postsSubscription.unsubscribe();
    }
}
