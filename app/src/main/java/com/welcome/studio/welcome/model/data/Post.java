package com.welcome.studio.welcome.model.data;

import com.welcome.studio.welcome.util.Constance;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Royal on 12.02.2017. !
 */

public class Post implements Serializable {
    private long id;
    private long userId;
    private String userName;
    private Rating userRating;
    private long time;
    private String city;
    private String address;
    private String description;
    private boolean dressCode;
    private double lat;
    private double lon;
    private Constance.PostType postType;
    private Constance.ContentType contentType;
    private String contentPath;
    private String contentRef;
    private Actions actions;
    private double offset;
    private long deleteTime;
    private List<String> tags;
    private String country;

    public Post(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Rating getUserRating() {
        return userRating;
    }

    public void setUserRating(Rating userRating) {
        this.userRating = userRating;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isDressCode() {
        return dressCode;
    }

    public void setDressCode(boolean dressCode) {
        this.dressCode = dressCode;
    }

    public long getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(long deleteTime) {
        this.deleteTime = deleteTime;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Constance.PostType getPostType() {
        return postType;
    }

    public void setPostType(Constance.PostType postType) {
        this.postType = postType;
    }

    public Constance.ContentType getContentType() {
        return contentType;
    }

    public void setContentType(Constance.ContentType contentType) {
        this.contentType = contentType;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getContentPath() {
        return contentPath;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

    public String getContentRef() {
        return contentRef;
    }

    public void setContentRef(String contentRef) {
        this.contentRef = contentRef;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Actions getActions() {
        return actions;
    }

    public void setActions(Actions actions) {
        this.actions = actions;
    }

    public double getOffset() {
        return offset;
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
