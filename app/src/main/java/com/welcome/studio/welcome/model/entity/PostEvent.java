package com.welcome.studio.welcome.model.entity;

import com.kelvinapps.rxfirebase.RxFirebaseChildEvent;
import com.welcome.studio.welcome.model.data.Post;

/**
 * Created by @mistreckless on 20.03.2017. !
 */

public class PostEvent {
    private Post post;
    private RxFirebaseChildEvent.EventType eventType;

    public PostEvent(){}

    public PostEvent(Post post, RxFirebaseChildEvent.EventType eventType) {
        this.post = post;
        this.eventType=eventType;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public RxFirebaseChildEvent.EventType getEventType() {
        return eventType;
    }

    public void setEventType(RxFirebaseChildEvent.EventType eventType) {
        this.eventType = eventType;
    }
}
