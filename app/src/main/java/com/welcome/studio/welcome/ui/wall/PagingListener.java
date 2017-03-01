package com.welcome.studio.welcome.ui.wall;

import com.welcome.studio.welcome.model.data.Post;

import java.util.List;

import rx.Observable;

/**
 * Created by @mistreckless on 22.02.2017. !
 */
@FunctionalInterface
public interface PagingListener {
    Observable<List<Post>> nextPage(int offset);
}
