package ru.yandex.weblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.weblog.model.Comment;
import ru.yandex.weblog.repository.CommentRepository;
import ru.yandex.weblog.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl (CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        return List.of();
    }

    @Override
    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId);
    }

    @Override
    public void editTextComment(Long commentId, String text) {
        Comment comment = getCommentById(commentId);
        comment.setText(text);
        comment.setCreatedAt(LocalDateTime.now());
        updateComment(comment);
    }

    @Override
    public void addComment(Long postId, String text) {
        Comment comment = new Comment();
        comment.setCreatedAt(LocalDateTime.now());
        comment.setText(text);
        comment.setPostId(postId);
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.delete(commentId);
    }

    @Override
    public void updateComment(Comment comment) {
        commentRepository.update(comment);
    }
}
