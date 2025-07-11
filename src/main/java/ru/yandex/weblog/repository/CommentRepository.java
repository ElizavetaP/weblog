package ru.yandex.weblog.repository;

import ru.yandex.weblog.model.Comment;

import java.util.List;

public interface CommentRepository {

    List<Comment> findByPostId(Long postId);

    Comment findById(Long commentId);

    void save(Comment comment);

    void update(Comment comment);

    void delete(Long commentId);
}
