package com.welcome.studio.welcome.util.search_view;

import android.support.v7.widget.SearchView;

import rx.Observable;
import rx.Subscriber;
import rx.android.MainThreadSubscription;

/**
 * Created by @mistreckless on 18.04.2017. !
 */

public final class CompactSearchViewQueryTextChangesOnSubscribe implements Observable.OnSubscribe<CharSequence> {
    private final SearchView searchView;

    public CompactSearchViewQueryTextChangesOnSubscribe(SearchView searchView) {
        this.searchView = searchView;
    }

    @Override
    public void call(Subscriber<? super CharSequence> subscriber) {
        MainThreadSubscription.verifyMainThread();
        SearchView.OnQueryTextListener watcher = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(query);
                    return true;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty() && !subscriber.isUnsubscribed()){
                    subscriber.onNext(newText);
                    return true;
                }
                return false;
            }

        };


        subscriber.add(new MainThreadSubscription() {
            @Override
            protected void onUnsubscribe() {
                searchView.setOnQueryTextListener(null);
            }
        });
        searchView.setOnQueryTextListener(watcher);
        subscriber.onNext(searchView.getQuery());
    }
}
