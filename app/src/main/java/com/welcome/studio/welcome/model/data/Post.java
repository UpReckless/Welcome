package com.welcome.studio.welcome.model.data;

import com.google.firebase.database.Exclude;
import com.welcome.studio.welcome.model.entity.Author;
import com.welcome.studio.welcome.util.Constance;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Royal on 12.02.2017. !
 */

public class Post implements Serializable {
    private String id;
    private Author author;
    private long time;
    private String city;
    private String address;
    private String description;
    private boolean dressCode;
    private double lat;
    private double lon;
    private Constance.PostType postType;
    private Constance.ContentType contentType;
    @Exclude
    private String contentPath;
    private String contentRef;
    private double offset;
    private long deleteTime;
    private List<String> tags;
    private String country;
    private Map<String,Like> likes;
    private Map<String,Willcome> willcomes;
    private Map<String,Report> reports;
    private Map<String,CommentModel> comments;
    @Exclude
    private boolean isLiked;
    @Exclude
    private boolean isWillcomed;
    @Exclude
    private boolean isReported;
    @Exclude
    private boolean isTryToUpload;

    public Post(){}

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
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

    @Exclude
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

    public Map<String, Like> getLikes() {
        return likes;
    }

    public void setLikes(Map<String, Like> likes) {
        this.likes = likes;
    }

    public Map<String,Willcome> getWillcomes() {
        return willcomes;
    }

    public void setWillcomes(Map<String,Willcome> willcomes) {
        this.willcomes = willcomes;
    }

    public Map<String,Report> getReports() {
        return reports;
    }

    public void setReports(Map<String,Report> reports) {
        this.reports = reports;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String,CommentModel> getComments() {
        return comments;
    }

    public void setComments(Map<String, CommentModel> comments) {
        this.comments = comments;
    }

    @Exclude
    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    @Exclude
    public boolean isWillcomed() {
        return isWillcomed;
    }

    public void setWillcomed(boolean willcomed) {
        isWillcomed = willcomed;
    }

    @Exclude
    public boolean isReported() {
        return isReported;
    }

    public void setReported(boolean reported) {
        isReported = reported;
    }

    @Exclude
    public boolean isTryToUpload() {
        return isTryToUpload;
    }

    public void setTryToUpload(boolean tryToUpload) {
        isTryToUpload = tryToUpload;
    }
}
