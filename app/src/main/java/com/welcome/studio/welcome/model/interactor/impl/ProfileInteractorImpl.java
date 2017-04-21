package com.welcome.studio.welcome.model.interactor.impl;

import com.kelvinapps.rxfirebase.RxFirebaseChildEvent;
import com.welcome.studio.welcome.app.RxBus;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.Rating;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.model.data.Willcome;
import com.welcome.studio.welcome.model.entity.PostEvent;
import com.welcome.studio.welcome.model.entity.UserRequest;
import com.welcome.studio.welcome.model.interactor.PostInteractor;
import com.welcome.studio.welcome.model.interactor.ProfileInteractor;
import com.welcome.studio.welcome.model.repository.FirebaseRepository;
import com.welcome.studio.welcome.model.repository.PostRepository;
import com.welcome.studio.welcome.model.repository.UserRepository;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Royal on 11.02.2017. !
 */

public class ProfileInteractorImpl implements ProfileInteractor {
    private UserRepository userRepository;
    private PostRepository postRepository;
    private FirebaseRepository firebaseRepository;
    private PostInteractor postInteractor;
    private RxBus rxBus;

    @Inject
    public ProfileInteractorImpl(UserRepository userRepository, PostRepository postRepository, FirebaseRepository firebaseRepository, RxBus rxBus, PostInteractor postInteractor) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.firebaseRepository = firebaseRepository;
        this.rxBus = rxBus;
        this.postInteractor = postInteractor;
    }

    @Override
    public User getUserCache() {
        return userRepository.getUserCache();
    }

    @Override
    public Observable<Rating> getRating(User user) {
        long id = user.getRating().getId();
        return userRepository.getRating(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Post>> getHistoryPosts(User user) {
        if (user.getId() == getUserCache().getId())
            return Observable.just(postRepository.getAllPosts())
                    .flatMap(posts -> filterHistoryPosts(user, posts))
                    .map(posts -> {
                        Collections.reverse(posts);
                        return posts;
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        else return postRepository.getHistoryPosts(generateUserRequest(user))
                .map(posts -> {
                    Collections.reverse(posts);
                    return posts;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Subscription listenPost(User user) {

        return firebaseRepository.listenPosts(user.getCountry(), user.getCity())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::realTimeProvider);
    }


    @Override
    public Observable<List<Post>> getNowPosts(User user) {
        if (user.getId() == getUserCache().getId())
            return Observable.just(postRepository.getAllPosts())
                    .flatMap(posts -> filterNowPosts(user, posts))
                    .map(posts -> {
                        Collections.reverse(posts);
                        return posts;
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        else return postRepository.getNowPosts(generateUserRequest(user))
                .map(posts -> {
                    Collections.reverse(posts);
                    return posts;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Observable<List<Post>> getWillcomedPost(User user) {
        if (user.getId() == getUserCache().getId())
            return Observable.just(postRepository.getAllPosts())
                    .flatMap(posts -> filterWillcomePosts(user, posts))
                    .map(posts -> {
                        Collections.reverse(posts);
                        return posts;
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        else return postRepository.getWillcomePosts(generateUserRequest(user))
                .map(posts -> {
                    Collections.reverse(posts);
                    return posts;
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

    private Observable<List<Post>> filterHistoryPosts(User user, List<Post> posts) {
        long time = System.currentTimeMillis();
        return Observable.from(posts)
                .filter(post -> time > post.getDeleteTime() && user.getId() == post.getAuthor().getuId())
                .toList()
                .subscribeOn(Schedulers.computation());
    }

    private Observable<List<Post>> filterNowPosts(User user, List<Post> posts) {
        long time = System.currentTimeMillis();
        return Observable.from(posts)
                .filter(post -> user.getId() == post.getAuthor().getuId() && time < post.getDeleteTime())
                .toList()
                .subscribeOn(Schedulers.computation());
    }

    private Observable<List<Post>> filterWillcomePosts(User user, List<Post> posts) {
        long time=System.currentTimeMillis();
        return Observable.from(posts)
                .filter(post -> {
                    if (post.getWillcomes() == null)
                        return false;
                    else for (Willcome willcome :
                            post.getWillcomes().values()) {
                        if (user.getId() == willcome.getAuthor().getuId() && time>post.getDeleteTime())
                            return true;
                    }
                    return false;
                })
                .toList()
                .subscribeOn(Schedulers.computation());
    }

    private UserRequest generateUserRequest(User user) {
        UserRequest userRequest = new UserRequest();
        userRequest.setId(user.getId());
        userRequest.setCity(user.getCity());
        userRequest.setCountry(user.getCountry());
        userRequest.setName(user.getNickname());
        userRequest.setToken(user.getToken());
        return userRequest;
    }

    private void realTimeProvider(RxFirebaseChildEvent<Post> rxFirebaseChildEvent) {
        switch (rxFirebaseChildEvent.getEventType()) {
            case ADDED:
                //do nothing
                break;
            case MOVED:
                //no idea
                break;
            case CHANGED:
                //update
                postRepository.update(rxFirebaseChildEvent.getValue());
                rxBus.sendPostEvent(new PostEvent(rxFirebaseChildEvent.getValue(), rxFirebaseChildEvent.getEventType()));
                break;
            case REMOVED:
                //send event
                rxBus.sendPostEvent(new PostEvent(rxFirebaseChildEvent.getValue(), rxFirebaseChildEvent.getEventType()));
                break;
        }
    }
}
