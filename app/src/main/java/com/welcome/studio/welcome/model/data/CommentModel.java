package com.welcome.studio.welcome.model.data;

import com.welcome.studio.welcome.model.entity.Author;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by @mistreckless on 16.02.2017. !
 */

public class CommentModel implements Serializable{
    private String id;
    private String text;
    private Map<String,Like> likes;
    private Author author;
    private long time;
    private boolean isLiked;

    public CommentModel(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<String,Like> getLikes() {
        return likes;
    }

    public void setLikes(Map<String,Like> likes) {
        this.likes = likes;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
