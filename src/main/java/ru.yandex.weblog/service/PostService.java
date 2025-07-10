package ru.yandex.weblog.service;

import ru.yandex.weblog.model.Post;

import java.util.List;


public interface PostService {


    public List<Post> findAll(String tag, int pageSize, int pageNumber);

    Post getPostById(Long id);

    public void addPost(Post post);

    public void deleteById(Long id);

    void likePost(Long id, boolean isLike);

}