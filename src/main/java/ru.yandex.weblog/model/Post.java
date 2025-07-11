package ru.yandex.weblog.model;

import java.util.List;

public class Post {
    private Long id;
    private String title;
    private String textPreview;
    private int likesCount;
    private String image;
    private List<Comment> comments;
    private List<String> tags;

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

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTagsAsText() {
        return String.join("#", tags);
    }
} 