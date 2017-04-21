package com.welcome.studio.welcome.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.welcome.studio.welcome.model.data.Rating;

import java.io.Serializable;

/**
 * Created by @mistreckless on 20.02.2017. !
 */

public class Author implements Serializable, Parcelable{
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


    protected Author(Parcel in) {
        uId = in.readLong();
        name = in.readString();
        rating = in.readParcelable(Rating.class.getClassLoader());
        thumbRef = in.readString();
    }

    public static final Creator<Author> CREATOR = new Creator<Author>() {
        @Override
        public Author createFromParcel(Parcel in) {
            return new Author(in);
        }

        @Override
        public Author[] newArray(int size) {
            return new Author[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(uId);
        dest.writeString(name);
        dest.writeParcelable(rating, flags);
        dest.writeString(thumbRef);
    }
}
