package ru.yandex.weblog.repository;

import ru.yandex.weblog.model.Post;
import java.util.List;

public interface PostRepository {

    List<Post> findAll(String tag, int pageSize, int pageNumber);

    void save(Post post);

    void deleteById(Long id);

    Post findById(Long id);

    void update(Post post);

    void likePost(Long id, boolean isLike);
}
