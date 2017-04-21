package com.welcome.studio.welcome.ui.module.search;

import android.support.v7.widget.RecyclerView;

import com.welcome.studio.welcome.app.Injector;
import com.welcome.studio.welcome.model.interactor.SearchInteractor;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.module.main.MainRouter;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by @mistreckless on 17.04.2017. !
 */

public class SearchPresenter extends BasePresenter<SearchView, MainRouter> {

    private SearchInteractor searchInteractor;
    private Subscription searchSubscription;

    @Inject
    SearchPresenter(SearchInteractor searchInteractor) {
        this.searchInteractor = searchInteractor;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    void querySearchLine(CharSequence line) {
        searchInteractor.searchQueryUsers(String.valueOf(line))
                .subscribe(getView()::addUsers,throwable -> {});
    }

    public void destroy() {
        if (searchSubscription != null)
            searchSubscription.unsubscribe();
        Injector.getInstance().clearSearchComponent();
    }

    void controlUsers(RecyclerView recyclerView) {
        if (searchSubscription == null || searchSubscription.isUnsubscribed())
            searchSubscription = searchInteractor.controlUsers(recyclerView)
                    .subscribe(getView()::addUsers);
    }
}
