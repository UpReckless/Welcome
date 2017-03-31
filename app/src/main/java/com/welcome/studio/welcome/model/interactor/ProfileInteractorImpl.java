package com.welcome.studio.welcome.model.interactor;

import com.kelvinapps.rxfirebase.RxFirebaseChildEvent;
import com.welcome.studio.welcome.app.RxBus;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.Rating;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.model.entity.PostEvent;
import com.welcome.studio.welcome.model.repository.FirebaseRepository;
import com.welcome.studio.welcome.model.repository.PostRepository;
import com.welcome.studio.welcome.model.repository.UserRepository;
import com.welcome.studio.welcome.util.Constance;

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
    private RxBus rxBus;

    @Inject
    public ProfileInteractorImpl(UserRepository userRepository, PostRepository postRepository, FirebaseRepository firebaseRepository, RxBus rxBus) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.firebaseRepository = firebaseRepository;
        this.rxBus = rxBus;
    }

    @Override
    public User getUserCache() {
        return userRepository.getUserCache();
    }

    @Override
    public Observable<Rating> getRating() {
        long id = getUserCache().getRating().getId();
        return userRepository.getRating(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Post>> getHistoryPosts() {
        User user = getUserCache();
        return Observable.just(postRepository.getAllPosts())
                .flatMap(posts -> filterHistoryPosts(user, posts))
                .map(posts -> {
                    Collections.reverse(posts);
                    return posts;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Subscription listenPost() {
        User user=getUserCache();
        return firebaseRepository.listenPosts(user.getCountry(),user.getCity())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::realTimeProvider);
    }


    @Override
    public Observable<List<Post>> getNowPosts() {
        User user=getUserCache();
        return Observable.just(postRepository.getAllPosts())
                .flatMap(posts->filterNowPosts(user,posts))
                .map(posts -> {
                    Collections.reverse(posts);
                    return posts;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<List<Post>> filterHistoryPosts(User user, List<Post> posts) {
        long time = System.currentTimeMillis();
        return Observable.from(posts)
                .filter(post -> time > post.getDeleteTime() && user.getId() == post.getAuthor().getuId())
                .buffer(Constance.ConstHolder.MAX_POST_LIMIT)
                .subscribeOn(Schedulers.computation());
    }

    private Observable<List<Post>> filterNowPosts(User user, List<Post> posts) {
        long time = System.currentTimeMillis();
        return Observable.from(posts)
                .filter(post -> user.getId() == post.getAuthor().getuId() && time < post.getDeleteTime())
                .buffer(Constance.ConstHolder.MAX_POST_LIMIT)
                .subscribeOn(Schedulers.computation());
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
