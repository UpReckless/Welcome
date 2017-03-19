package com.welcome.studio.welcome.model.entity;

/**
 * Created by @mistreckless on 06.03.2017. !
 */

public class UpdateUserResponse {
    private String name;
    private String email;
    private String photoRef;

    public UpdateUserResponse(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoRef() {
        return photoRef;
    }

    public void setPhotoRef(String photoRef) {
        this.photoRef = photoRef;
    }
}
