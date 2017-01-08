package com.welcome.studio.welcome.model.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;


public class User {

    private long id;
    private String nickname;
    private String email;
    private String photoRef;
    @JsonIgnore
    private String photoPath;
    private String imei;
    @JsonSerialize(as = Raiting.class)
    private Raiting raiting;
    @JsonIgnore private List<ArchivePhoto> archivePhotos;
    private double latitude;
    private double longitude;
    private String city;

    public User(){}

    public User(String nickname, String imei, String email, double latitude, double longitude) {
        this.nickname = nickname;
        this.imei = imei;
        this.email = email;
        this.latitude=latitude;
        this.longitude=longitude;
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

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Raiting getRaiting() {
        return raiting;
    }

    public void setRaiting(Raiting raiting) {
        this.raiting = raiting;
    }

    public List<ArchivePhoto> getArchivePhotos() {
        return archivePhotos;
    }

    public void setArchivePhotos(List<ArchivePhoto> archivePhotos) {
        this.archivePhotos = archivePhotos;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
