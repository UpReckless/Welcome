package com.welcome.studio.welcome.app;

import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.entity.PostEvent;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by @mistreckless on 19.03.2017. !
 */

public final class RxBus {
    private final Subject<PostEvent, PostEvent> postEventBus = new SerializedSubject<>(PublishSubject.create());
    private final Subject<Post, Post> userPostBus = new SerializedSubject<>(PublishSubject.create());

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

    public boolean hasObservers(Subject subject) {
        return subject.hasObservers();
    }
}
