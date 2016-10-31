package com.welcome.studio.welcome.model.entity;

import java.util.List;

/**
 * Created by Royal on 18.10.2016.
 */
public class User {

    private long id;
    private String nickname;
    private String email;
    private String photoRef;
    private String imei;
    private Raiting raiting;
    //private List<ArchivePhoto> archivePhotos;

    public User(){}

    public User(String nickname, String email, String imei) {
        this.nickname = nickname;
        this.email = email;
        this.imei = imei;
        this.raiting = raiting;
    }
    public User(String nickname, String imei) {
        this.nickname = nickname;
        this.imei = imei;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public Raiting getRaiting() {
        return raiting;
    }

    public void setRaiting(Raiting raiting) {
        this.raiting = raiting;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

//    public List<ArchivePhoto> getArchivePhotos() {
//        return archivePhotos;
//    }
//
//    public void setArchivePhotos(List<ArchivePhoto> archivePhotos) {
//        this.archivePhotos = archivePhotos;
//    }
}
