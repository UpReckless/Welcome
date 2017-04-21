package com.welcome.studio.welcome.model.interactor.impl;

import com.welcome.studio.welcome.model.data.Like;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.Report;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.model.data.Willcome;
import com.welcome.studio.welcome.model.entity.Author;
import com.welcome.studio.welcome.model.interactor.PostInteractor;
import com.welcome.studio.welcome.model.repository.FirebaseRepository;
import com.welcome.studio.welcome.model.repository.UserRepository;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by @mistreckless on 06.04.2017. !
 */

public class PostInteractorImpl implements PostInteractor {
    private UserRepository userRepository;
    private FirebaseRepository firebaseRepository;

    @Inject
    public PostInteractorImpl(UserRepository userRepository, FirebaseRepository firebaseRepository) {
        this.userRepository = userRepository;
        this.firebaseRepository = firebaseRepository;
    }

    @Override
    public Observable<Boolean> changeLikeCount(Post post) {
        User user = userRepository.getUserCache();
        return userRepository.checkServerConnection()
                .switchMap(connect -> connect ? post.isLiked() ? firebaseRepository.decLikeCount(post, findUserLike(user, post)) :
                        firebaseRepository.incLikeCount(post, generateLike(user)) : Observable.just(false))
                .subscribeOn(Schedulers.io())
                .onErrorReturn(throwable -> false)
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Boolean> changeWillcomeCount(Post post) {
        User user = userRepository.getUserCache();
        if (post.getAuthor().getuId() == user.getId())
            return Observable.just(true);
        else
            return userRepository.checkServerConnection()
                    .switchMap(connect -> connect ? post.isWillcomed() ? firebaseRepository.decWillcomeCount(post, findUserWillcome(user, post)) :
                            firebaseRepository.incWillcomeCount(post, generateWillcome(user))
                                    .doOnNext(success-> userRepository.showWillcomeNotification(post)) : Observable.just(false))
                    .subscribeOn(Schedulers.io())
                    .onErrorReturn(throwable -> false)
                    .observeOn(AndroidSchedulers.mainThread());
    }


    @Override
    public Observable<Boolean> changeReportCount(Post post) {
        User user = userRepository.getUserCache();
        return userRepository.checkServerConnection()
                .switchMap(connect -> connect ? post.isReported() ? firebaseRepository.decReportCount(post, findUserReport(user, post)) :
                        firebaseRepository.incReportCount(post, generateReport(user)) : Observable.just(false))
                .subscribeOn(Schedulers.io())
                .onErrorReturn(throwable -> false)
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Like findUserLike(User user, Post post) {
        if (post.getLikes() != null)
            for (Like like :
                    post.getLikes().values()) {
                if (user.getId() == like.getAuthor().getuId())
                    return like;
            }
        return null;
    }

    private Like generateLike(User user) {
        Like like = new Like();
        like.setAuthor(new Author(user.getId(), user.getNickname(), user.getRating(), user.getPhotoRef()));
        return like;
    }

    private Willcome findUserWillcome(User user, Post post) {
        if (post.getWillcomes() != null)
            for (Willcome willcome :
                    post.getWillcomes().values()) {
                if (user.getId() == willcome.getAuthor().getuId())
                    return willcome;
            }
        return null;
    }

    private Willcome generateWillcome(User user) {
        Willcome willcome = new Willcome();
        willcome.setAuthor(new Author(user.getId(), user.getNickname(), user.getRating(), user.getPhotoRef()));
        return willcome;
    }

    private Report findUserReport(User user, Post post) {
        if (post.getReports() != null)
            for (Report report :
                    post.getReports().values()) {
                if (user.getId() == report.getAuthor().getuId())
                    return report;
            }
        return null;
    }

    private Report generateReport(User user) {
        Report report = new Report();
        report.setAuthor(new Author(user.getId(), user.getNickname(), user.getRating(), user.getPhotoRef()));
        return report;
    }

}
