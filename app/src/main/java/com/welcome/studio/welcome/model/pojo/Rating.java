package com.welcome.studio.welcome.model.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Rating {

    private long id;
    @JsonProperty("user")
    private User user;
    private long likeCount;
    private long willcomeCount;
    private long postCount;
    private long vippostCount;

    public Rating(){}

    public Rating(User user, long likeCount, long willcomeCount, long postCount,long vippostCount) {
        this.user = user;
        this.likeCount = likeCount;
        this.willcomeCount = willcomeCount;
        this.postCount = postCount;
        this.vippostCount = vippostCount;
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

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public long getWillcomeCount() {
        return willcomeCount;
    }

    public void setWillcomeCount(Long willcomeCount) {
        this.willcomeCount = willcomeCount;
    }

    public long getPostCount() {
        return postCount;
    }

    public void setPostCount(Integer postCount) {
        this.postCount = postCount;
    }

    public long getVippostCount() {
        return vippostCount;
    }

    public void setVippostCount(Integer vippostCount) {
        this.vippostCount = vippostCount;
    }
}
