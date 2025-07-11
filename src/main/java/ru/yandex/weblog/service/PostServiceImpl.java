package ru.yandex.weblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.weblog.model.Post;
import ru.yandex.weblog.repository.PostRepository;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> findAll(String tag, int pageSize, int pageNumber) {
        return postRepository.findAll(tag, pageSize, pageNumber);
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public void addPost(Post post) {
        postRepository.save(post);
    }

    @Override
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public void likePost(Long id, boolean isLike) {
        postRepository.likePost(id, isLike);
    }

    @Override
    public void editPost(Post post) {
        postRepository.update(post);
    }

} 