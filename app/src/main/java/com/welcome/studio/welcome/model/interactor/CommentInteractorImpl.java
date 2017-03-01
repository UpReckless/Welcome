package com.welcome.studio.welcome.model.interactor;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.kelvinapps.rxfirebase.RxFirebaseChildEvent;
import com.welcome.studio.welcome.model.data.Author;
import com.welcome.studio.welcome.model.data.CommentModel;
import com.welcome.studio.welcome.model.data.Like;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.model.repository.FirebaseRepository;
import com.welcome.studio.welcome.model.repository.UserRepository;
import com.welcome.studio.welcome.ui.comment.CommentAdapter;
import com.welcome.studio.welcome.ui.comment.PagingListener;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by @mistreckless on 28.02.2017. !
 */

public class CommentInteractorImpl implements CommentInteractor {
    private UserRepository userRepository;
    private FirebaseRepository firebaseRepository;

    @Inject
    public CommentInteractorImpl(UserRepository userRepository, FirebaseRepository firebaseRepository) {
        this.userRepository = userRepository;
        this.firebaseRepository = firebaseRepository;
    }

    @Override
    public Observable<RxFirebaseChildEvent<CommentModel>> listenComments(Post post) {
        return Observable.just(getUserCache())
                .switchMap(user -> firebaseRepository.listenComments(post));
    }

    @Override
    public Observable<Boolean> controlSendView(Observable<CharSequence> textFieldListener) {
        return textFieldListener.map(text -> !TextUtils.isEmpty(text));
    }

    @Override
    public Observable<Boolean> sendComment(Post post, String text) {
        return Observable.just(getUserCache())
                .map(user -> generateCommentModel(user, text))
                .switchMap(commentModel -> firebaseRepository.sendComment(post, commentModel));
    }

    @Override
    public Observable<Boolean> incLikeCount(CommentModel comment, Post post) {
        return Observable.just(getUserCache())
                .map(this::generateLike)
                .switchMap(like -> firebaseRepository.incLikeCount(post, comment, like));
    }

    @Override
    public Observable<Boolean> decLikeCount(CommentModel comment, Post post) {
        return Observable.just(getUserCache())
                .map(user -> findUserLike(comment, user))
                .switchMap(like -> firebaseRepository.decLikeCount(post, comment, like));
    }

    @Override
    public User getUserCache() {
        return userRepository.getUserCache();
    }

    private CommentModel generateCommentModel(User user, String text) {
        CommentModel commentModel = new CommentModel();
        commentModel.setText(text);
        commentModel.setTime(System.currentTimeMillis());
        commentModel.setAuthor(new Author(user.getId(), user.getNickname(), user.getRating(), user.getPhotoRef()));
        return commentModel;
    }

    private Like generateLike(User user) {
        Like like = new Like();
        like.setAuthor(new Author(user.getId(), user.getNickname(), user.getRating(), user.getPhotoRef()));
        return like;
    }

    private Like findUserLike(CommentModel comment, User user) {
        for (Like like :
                comment.getLikes().values()) {
            if (user.getId() == like.getAuthor().getuId())
                return like;
        }
        return null;
    }
}
