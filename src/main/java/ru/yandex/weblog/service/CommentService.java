package ru.yandex.weblog.service;

import ru.yandex.weblog.model.Comment;
import java.util.List;

public interface CommentService {

    List<Comment> getCommentsByPostId(Long postId);

    Comment getCommentById(Long commentId);

    void editTextComment(Long commentId, String text);

    void addComment(Long postId, String text);

    void deleteComment(Long commentId);

    void updateComment(Comment comment);
}