package com.welcome.studio.welcome.model.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by @mistreckless on 15.02.2017. !
 */

public class Actions implements Serializable {
    private List<Like> likes;
    private List<Willcome> willcomes;
    private List<Report> reports;
    private List<Comment> comments;

    public Actions(){}

    public Actions(List<Like> likes, List<Report> reports, List<Willcome> willcomes, List<Comment> comments) {
        this.likes = likes;
        this.reports = reports;
        this.willcomes = willcomes;
        this.comments = comments;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public List<Willcome> getWillcomes() {
        return willcomes;
    }

    public void setWillcomes(List<Willcome> willcomes) {
        this.willcomes = willcomes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }
}
