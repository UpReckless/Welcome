package com.welcome.studio.welcome.model.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by Royal on 18.10.2016.
 */
public class ArchivePhoto {
    private long id;
    private User user;
    @JsonIgnore
    private String photoPath;
    private String photoRef;

    public ArchivePhoto(){}

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

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getPhotoRef() {
        return photoRef;
    }

    public void setPhotoRef(String photoRef) {
        this.photoRef = photoRef;
    }
}
