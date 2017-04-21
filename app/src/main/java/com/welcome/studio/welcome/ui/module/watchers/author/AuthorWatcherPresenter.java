package com.welcome.studio.welcome.ui.module.watchers.author;

import com.welcome.studio.welcome.app.Injector;
import com.welcome.studio.welcome.model.entity.Author;
import com.welcome.studio.welcome.model.interactor.MainInteractor;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.module.main.MainRouter;

import javax.inject.Inject;

/**
 * Created by @mistreckless on 12.04.2017. !
 */

public class AuthorWatcherPresenter extends BasePresenter<AuthorWatcherView,MainRouter> {

    private MainInteractor mainInteractor;

    @Inject
    AuthorWatcherPresenter(MainInteractor mainInteractor){
        this.mainInteractor=mainInteractor;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    void authorClicked(Author author) {
        mainInteractor.getUser(author)
                .subscribe(user->getRouter().navigateToProfile(user,user.getId()!=mainInteractor.getUserCache().getId()));
    }

    public void destroy() {
        Injector.getInstance().clearAuthorWatcherComponent();
    }
}
