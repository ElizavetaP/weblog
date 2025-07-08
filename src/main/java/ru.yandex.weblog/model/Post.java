package ru.yandex.weblog.model;

import java.util.List;

public class Post {
    private Long id;
    private String title;
    private String textPreview;
    private String imagePath;
    private int likesCount;
    private java.util.List<Comment> comments;
    private java.util.List<String> tags;

    public Post() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTextPreview() {
        return textPreview;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTextPreview(String textPreview) {
        this.textPreview = textPreview;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

} 