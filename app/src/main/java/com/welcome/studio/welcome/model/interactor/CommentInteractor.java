package com.welcome.studio.welcome.model.interactor;

import com.kelvinapps.rxfirebase.RxFirebaseChildEvent;
import com.welcome.studio.welcome.model.data.CommentModel;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.User;

import rx.Observable;

/**
 * Created by @mistreckless on 28.02.2017. !
 */
public interface CommentInteractor {
    Observable<RxFirebaseChildEvent<CommentModel>> listenComments(Post post);

    Observable<Boolean> controlSendView(Observable<CharSequence> textFieldListener);

    Observable<Boolean> sendComment(Post post, String text);

    Observable<Boolean> incLikeCount(CommentModel comment, Post post);

    Observable<Boolean> decLikeCount(CommentModel comment, Post post);

    User getUserCache();

    Observable<Boolean> checkServerConnection();
}
