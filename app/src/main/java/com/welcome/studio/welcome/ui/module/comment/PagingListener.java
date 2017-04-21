package com.welcome.studio.welcome.ui.module.comment;

import com.welcome.studio.welcome.model.data.CommentModel;

import java.util.List;

import rx.Observable;

/**
 * Created by @mistreckless on 01.03.2017. !
 */
@FunctionalInterface
public interface PagingListener {
    Observable<List<CommentModel>> nextPage(int offset);
}
