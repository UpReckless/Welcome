package com.welcome.studio.welcome.ui.module.search;

import com.welcome.studio.welcome.model.entity.UserResponse;

import java.util.List;

import rx.Observable;

/**
 * Created by @mistreckless on 19.04.2017. !
 */
@FunctionalInterface
public interface PaginListener {
    Observable<List<UserResponse>> nextPage(int offset);
}
