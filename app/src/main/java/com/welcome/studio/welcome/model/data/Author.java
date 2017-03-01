package com.welcome.studio.welcome.model.data;

import java.io.Serializable;

/**
 * Created by @mistreckless on 20.02.2017. !
 */

public class Author implements Serializable{
    private long uId;
    private String name;
    private Rating rating;
    private String thumbRef;

    public Author(){}

    public Author(long uId, String name, Rating rating, String thumbRef) {
        this.uId = uId;
        this.name = name;
        this.rating = rating;
        this.thumbRef = thumbRef;
    }

    public long getuId() {
        return uId;
    }

    public void setuId(long uId) {
        this.uId = uId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public String getThumbRef() {
        return thumbRef;
    }

    public void setThumbRef(String thumbRef) {
        this.thumbRef = thumbRef;
    }
}
