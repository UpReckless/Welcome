package com.welcome.studio.welcome.model.interactor.impl;

import android.util.Log;

import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.model.interactor.PostInteractor;
import com.welcome.studio.welcome.model.interactor.PostWatcherInteractor;
import com.welcome.studio.welcome.model.repository.FirebaseRepository;
import com.welcome.studio.welcome.model.repository.PostRepository;
import com.welcome.studio.welcome.model.repository.UserRepository;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by @mistreckless on 06.04.2017. !
 */

public class PostWatcherInteractorImpl implements PostWatcherInteractor {
    private UserRepository userRepository;
    private FirebaseRepository firebaseRepository;
    private PostRepository postRepository;
    private PostInteractor postInteractor;

    @Inject
    public PostWatcherInteractorImpl(UserRepository userRepository, FirebaseRepository firebaseRepository, PostRepository postRepository, PostInteractor postInteractor) {
        this.userRepository = userRepository;
        this.firebaseRepository = firebaseRepository;
        this.postRepository = postRepository;
        this.postInteractor = postInteractor;
    }

    @Override
    public Observable<Post> listenPost(Post post) {
        User user = userRepository.getUserCache();
        return firebaseRepository.listenPost(post.getCountry(), post.getCity(), post.getId())
                .map(post1 -> {
                    postRepository.update(post1);
                    return post1;
                })
                .onErrorReturn(throwable -> {
                    Log.e("WATCHERINTERACTOR",throwable.getMessage());
                    if (post.getAuthor().getuId() != user.getId())
                        postRepository.removePost(post.getId());
                    return null;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Boolean> changeLikeCount(Post post) {
        return postInteractor.changeLikeCount(post);
    }

    @Override
    public Observable<Boolean> changeWillcomeCount(Post post) {
        return postInteractor.changeWillcomeCount(post);
    }

    @Override
    public Observable<Boolean> changeReportCount(Post post) {
        return postInteractor.changeReportCount(post);
    }

    @Override
    public User getUserCache() {
        return userRepository.getUserCache();
    }
}
