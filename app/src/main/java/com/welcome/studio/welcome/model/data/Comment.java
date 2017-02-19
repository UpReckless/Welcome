package com.welcome.studio.welcome.model.data;

import java.io.Serializable;

/**
 * Created by @mistreckless on 16.02.2017. !
 */

public class Comment implements Serializable{
    private long id;
    private long uId;
    private String name;
    private String thumbRef;
    private String text;
    private long likeCount;
    private int muteCount;

    public Comment(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getThumbRef() {
        return thumbRef;
    }

    public void setThumbRef(String thumbRef) {
        this.thumbRef = thumbRef;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public int getMuteCount() {
        return muteCount;
    }

    public void setMuteCount(int muteCount) {
        this.muteCount = muteCount;
    }
}
