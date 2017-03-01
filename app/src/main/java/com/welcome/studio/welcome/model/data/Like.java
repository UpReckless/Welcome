package com.welcome.studio.welcome.model.data;

import java.io.Serializable;

/**
 * Created by @mistreckless on 15.02.2017. !
 */

public class Like implements Serializable {
    private Author author;
    private String key;

    public Like() {
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
