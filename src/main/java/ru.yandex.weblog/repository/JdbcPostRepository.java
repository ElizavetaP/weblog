package ru.yandex.weblog.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.weblog.model.Comment;
import ru.yandex.weblog.model.Post;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcPostRepository implements PostRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcPostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Post> findAll() {
        String sql = "SELECT p.id AS post_id, p.title, p.textPreview, p.likesCount, " +
                "c.id AS comment_id, c.text AS comment_text, c.author, c.created_at " +
                "FROM post p LEFT JOIN comment c ON p.id = c.post_id";

        Map<Long, Post> postMap = new LinkedHashMap<>();

        jdbcTemplate.query(sql, rs -> {
            long postId = rs.getLong("post_id");

            Post post = postMap.get(postId);
            if (post == null) {
                post = new Post();
                post.setId(postId);
                post.setTitle(rs.getString("title"));
                post.setTextPreview(rs.getString("textPreview"));
                post.setLikesCount(rs.getInt("likesCount"));
                post.setComments(new ArrayList<>());
                postMap.put(postId, post);
            }

            // Обработка комментария (если есть)
            long commentId = rs.getLong("comment_id");
            if (commentId != 0) {
                Comment comment = new Comment();
                comment.setId(commentId);
                comment.setText(rs.getString("comment_text"));
                comment.setAuthor(rs.getString("author"));
                Timestamp ts = rs.getTimestamp("created_at");
                if (ts != null) comment.setCreatedAt(ts.toLocalDateTime());
                comment.setPostId(postId);
                post.getComments().add(comment);
            }
        });

        return new ArrayList<>(postMap.values());
    }

    @Override
    public List<Post> findAll(String search, int pageSize, int pageNumber) {
        return List.of();
    }

    public Post findById(Long id){
        return jdbcTemplate.query(
                "select id, title, textPreview, likesCount from post where id = ?",
                (rs, rowNum) -> new Post(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("textPreview"),
                        rs.getInt("likesCount")
                ),
                id).getFirst();
    }

    @Override
    public void update(Post post) {

    }

    @Override
    public void save(Post post) {
        // Формируем insert-запрос с параметрами
        jdbcTemplate.update("insert into post(title, textPreview) values(?, ?)",
                post.getTitle(), post.getTextPreview());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("delete from post where id = ?", id);
    }
}
