package com.welcome.studio.welcome.ui.profile.history;

import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.interactor.ProfileInteractor;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.main.MainRouter;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by @mistreckless on 16.01.2017. !
 */

public class HistoryPresenter extends BasePresenter<HistoryView,MainRouter> {
    private ProfileInteractor profileInteractor;
    private Subscription postsSubscription;

    @Inject
    HistoryPresenter(ProfileInteractor profileInteractor){
        this.profileInteractor=profileInteractor;
    }

    @Override
    public void onStart() {
        if (postsSubscription==null || postsSubscription.isUnsubscribed())
            postsSubscription=profileInteractor.getHistoryPosts()
                    .subscribe(getView()::setPostsToAdapter);
    }

    @Override
    public void onStop() {

    }

    void postClicked(Post post) {
        getRouter().navigateToPostWatcher(post);
    }

    void destroy() {
        postsSubscription.unsubscribe();
    }
}
