package com.welcome.studio.welcome.model.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Raiting {

    private long id;
    @JsonProperty("user")
    private User user;
    private Long likeCount;
    private Long willcomeCount;
    private Integer postCount;
    private Integer vippostCount;

    public Raiting(){}

    public Raiting(User user, Long likeCount, Long willcomeCount, Integer postCount, Integer vippostCount) {
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

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public Long getWillcomeCount() {
        return willcomeCount;
    }

    public void setWillcomeCount(Long willcomeCount) {
        this.willcomeCount = willcomeCount;
    }

    public Integer getPostCount() {
        return postCount;
    }

    public void setPostCount(Integer postCount) {
        this.postCount = postCount;
    }

    public Integer getVippostCount() {
        return vippostCount;
    }

    public void setVippostCount(Integer vippostCount) {
        this.vippostCount = vippostCount;
    }
}
