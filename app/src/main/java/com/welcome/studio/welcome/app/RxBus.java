package com.welcome.studio.welcome.app;

import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.entity.PostEvent;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by @mistreckless on 19.03.2017. !
 */

public final class RxBus {
    private final Subject<List<Post>, List<Post>> postListBus = new SerializedSubject<>(PublishSubject.create());
    private final Subject<PostEvent, PostEvent> postEventBus = new SerializedSubject<>(PublishSubject.create());
    private final Subject<Post, Post> userPostBus = new SerializedSubject<>(PublishSubject.create());
//    private final Subject<PostEvent, PostEvent> profilePostEvent = new SerializedSubject<>(PublishSubject.create());

    public void sendPostList(List<Post> posts) {
        postListBus.onNext(posts);
    }

    public Observable<List<Post>> getPostList() {
        return postListBus.observeOn(AndroidSchedulers.mainThread());
    }

    public void sendPostEvent(PostEvent postEvent) {
        postEventBus.onNext(postEvent);
    }

    public Observable<PostEvent> getPostEvent() {
        return postEventBus.observeOn(AndroidSchedulers.mainThread());
    }

    public void sendUserPost(Post post) {
        userPostBus.onNext(post);
    }

    public Observable<Post> getUserPostEvent() {
        return userPostBus.observeOn(AndroidSchedulers.mainThread());
    }

//    public void setProfilePostEvent(PostEvent postEvent) {
//        profilePostEvent.onNext(postEvent);
//    }
//
//    public Observable<PostEvent> getProfilePostEvent() {
//        return profilePostEvent;
//    }

    public boolean hasObservers(Subject subject) {
        return subject.hasObservers();
    }
}
