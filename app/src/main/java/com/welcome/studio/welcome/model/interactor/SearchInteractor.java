package com.welcome.studio.welcome.model.interactor;

import android.support.v7.widget.RecyclerView;

import com.welcome.studio.welcome.model.data.User;

import java.util.List;

import rx.Observable;

/**
 * Created by @mistreckless on 19.04.2017. !
 */

public interface SearchInteractor {

    Observable<List<User>> controlUsers(RecyclerView recyclerView);

    Observable<List<User>> searchQueryUsers(String searchLine);

}
