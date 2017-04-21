package com.welcome.studio.welcome.model.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;


public class Rating implements Serializable, Parcelable{

    private long id;
    private long likeCount;
    private long willcomeCount;
    private long postCount;
    private long vippostCount;

    public Rating(){}

    public Rating(long id,long likeCount, long willcomeCount, long postCount,long vippostCount) {
        this.id=id;
        this.likeCount = likeCount;
        this.willcomeCount = willcomeCount;
        this.postCount = postCount;
        this.vippostCount = vippostCount;
    }

    protected Rating(Parcel in) {
        id = in.readLong();
        likeCount = in.readLong();
        willcomeCount = in.readLong();
        postCount = in.readLong();
        vippostCount = in.readLong();
    }

    public static final Creator<Rating> CREATOR = new Creator<Rating>() {
        @Override
        public Rating createFromParcel(Parcel in) {
            return new Rating(in);
        }

        @Override
        public Rating[] newArray(int size) {
            return new Rating[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(likeCount);
        dest.writeLong(willcomeCount);
        dest.writeLong(postCount);
        dest.writeLong(vippostCount);
    }
}
