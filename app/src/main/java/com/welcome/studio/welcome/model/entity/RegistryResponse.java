package com.welcome.studio.welcome.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.welcome.studio.welcome.model.data.Rating;

/**
 * Created by @mistreckless on 06.03.2017. !
 */

public class RegistryResponse {
    private long id;
    private String name;
    @JsonSerialize(as = Rating.class)
    private Rating rating;

    public RegistryResponse(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
