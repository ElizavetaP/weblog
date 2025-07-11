package ru.yandex.weblog.service;

import ru.yandex.weblog.model.Post;

import java.util.List;


public interface PostService {


    public List<Post> findAll(String tag, int pageSize, int pageNumber);

    public Post getPostById(Long id);

    public void addPost(Post post);

    public void deleteById(Long id);

    public void likePost(Long id, boolean isLike);

    public void editPost(Post post);

}