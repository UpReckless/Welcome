package com.welcome.studio.welcome.util.search_view;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.v7.widget.SearchView;

import com.jakewharton.rxbinding.internal.Preconditions;

import rx.Observable;

/**
 * Created by @mistreckless on 18.04.2017. !
 */

public final class RxCompactSearchView {

    @CheckResult
    @NonNull
    public static Observable<CharSequence> queryTextChanged(@NonNull SearchView searchView){
        Preconditions.checkNotNull(searchView,"view == null");
        return Observable.create(new CompactSearchViewQueryTextChangesOnSubscribe(searchView));
    }
}
