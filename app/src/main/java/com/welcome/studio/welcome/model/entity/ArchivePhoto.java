package com.welcome.studio.welcome.model.entity;

/**
 * Created by Royal on 18.10.2016.
 */
public class ArchivePhoto {
    private long id;
    private User user;

    public ArchivePhoto(){}

    public ArchivePhoto(long id, User user) {
        this.id = id;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
