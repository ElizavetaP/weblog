package ru.yandex.weblog.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.weblog.model.Comment;

import java.util.List;

@Repository
public class JdbcCommentRepository implements CommentRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCommentRepository (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Comment> findByPostId(Long postId) {
        return List.of();
    }

    @Override
    public Comment findById(Long commentId) {
        String sql = "SELECT id, post_id, text, author, created_at FROM comment WHERE id = ?";
        return jdbcTemplate.query(sql, rs -> {
            if (rs.next()) {
                Comment comment = new Comment();
                comment.setId(rs.getLong("id"));
                comment.setPostId(rs.getLong("post_id"));
                comment.setText(rs.getString("text"));
                comment.setAuthor(rs.getString("author"));
                java.sql.Timestamp ts = rs.getTimestamp("created_at");
                if (ts != null) comment.setCreatedAt(ts.toLocalDateTime());
                return comment;
            }
            return null;
        }, commentId);
    }

    @Override
    public Comment save(Comment comment) {
        return null;
    }

    @Override
    public void update(Comment comment) {
        jdbcTemplate.update("UPDATE comment SET text = ? WHERE id = ?",
                comment.getText(),
                comment.getId()
        );
    }

    @Override
    public void delete(Long commentId) {

    }
}
