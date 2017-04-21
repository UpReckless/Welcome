package com.welcome.studio.welcome.model.interactor;

import com.welcome.studio.welcome.model.data.Post;

import rx.Observable;

/**
 * Created by @mistreckless on 06.04.2017. !
 */

public interface PostInteractor {

    Observable<Boolean> changeLikeCount(Post post);

    Observable<Boolean> changeWillcomeCount(Post post);

    Observable<Boolean> changeReportCount(Post post);
}
