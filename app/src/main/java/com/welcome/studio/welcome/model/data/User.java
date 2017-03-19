package com.welcome.studio.welcome.model.data;

import java.io.Serializable;


public class User implements Serializable {

    private long id;
    private String nickname;
    private String email;
    private String photoRef;
    private String photoPath;
    private String imei;
    private Rating rating;
    private String city;
    private String token;
    private String country;

    public User(){}

    public User(String nickname, String imei, String city,String country) {
        this.nickname = nickname;
        this.imei = imei;
        this.city=city;
        this.country=country;
    }

    public User(long id, String nickname, String email, String photoRef, String photoPath,
                String imei, Rating rating, String city, String token, String country) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.photoRef = photoRef;
        this.photoPath = photoPath;
        this.imei = imei;
        this.rating = rating;
        this.city = city;
        this.token = token;
        this.country = country;
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

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
