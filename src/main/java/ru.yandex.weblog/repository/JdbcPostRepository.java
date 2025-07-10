package ru.yandex.weblog.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.weblog.model.Comment;
import ru.yandex.weblog.model.Post;

import java.sql.Timestamp;
import java.util.*;

@Repository
public class JdbcPostRepository implements PostRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcPostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Post> findAll(String tag, int pageSize, int pageNumber) {
        String sql = """
                SELECT p.id AS post_id,
                       p.title,
                       p.textPreview,
                       p.likesCount,
                
                       c.id AS comment_id,
                       c.text AS comment_text,
                       c.author,
                       c.created_at,
                
                       t.id AS tag_id,
                       t.name AS tag_name
                
                FROM (SELECT DISTINCT p.id
                      FROM post p
                      JOIN post_tag pt ON p.id = pt.post_id
                      JOIN tag t ON pt.tag_id = t.id
                      WHERE t.name LIKE ?
                      ORDER BY p.id
                      LIMIT ? OFFSET ?
                      ) ids
                JOIN post p ON p.id = ids.id
                LEFT JOIN comment c ON p.id = c.post_id
                LEFT JOIN post_tag pt ON p.id = pt.post_id
                LEFT JOIN tag t ON pt.tag_id = t.id
                """;

        Map<Long, Post> postMap = new LinkedHashMap<>();
        Map<Long, Set<Long>> postCommentIds = new HashMap<>();

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
                post.setTags(new ArrayList<>());
                postMap.put(postId, post);
                postCommentIds.put(postId, new HashSet<>());
            }

            // Обработка комментария (если есть)
            long commentId = rs.getLong("comment_id");
            if (commentId != 0 && !postCommentIds.get(postId).contains(commentId)) {
                Comment comment = new Comment();
                comment.setId(commentId);
                comment.setText(rs.getString("comment_text"));
                comment.setAuthor(rs.getString("author"));
                Timestamp ts = rs.getTimestamp("created_at");
                if (ts != null) comment.setCreatedAt(ts.toLocalDateTime());
                comment.setPostId(postId);
                post.getComments().add(comment);
                postCommentIds.get(postId).add(commentId);
            }

            // Тег
            String tagName = rs.getString("tag_name");
            if (tagName != null && !post.getTags().contains(tagName)) {
                post.getTags().add(tagName);
            }
        }, "%" + tag + "%", pageSize, (pageNumber - 1)*pageSize);

        return new ArrayList<>(postMap.values());
    }

    @Override
    public Post findById(Long id) {
        String sql = """
                SELECT p.id as post_id, p.title, p.textPreview, p.likesCount,
                    c.id as comment_id, c.text as comment_text, c.author, c.created_at,
                    t.name as tag_name
                FROM post p
                LEFT JOIN comment c ON p.id = c.post_id
                LEFT JOIN post_tag pt ON p.id = pt.post_id
                LEFT JOIN tag t ON pt.tag_id = t.id
                WHERE p.id = ?
                """;
        return jdbcTemplate.query(sql, rs -> {
            Post post = null;
            Set<String> tagNames = new HashSet<>();
            Set<Long> commentIds = new HashSet<>();
            while (rs.next()) {
                if (post == null) {
                    post = new Post();
                    post.setId(rs.getLong("post_id"));
                    post.setTitle(rs.getString("title"));
                    post.setTextPreview(rs.getString("textPreview"));
                    post.setLikesCount(rs.getInt("likesCount"));
                    post.setComments(new ArrayList<>());
                    post.setTags(new ArrayList<>());
                }

                // Обработка комментария (если есть)
                long commentId = rs.getLong("comment_id");
                if (commentId != 0 && !commentIds.contains(commentId)) {
                    Comment comment = new Comment();
                    comment.setId(commentId);
                    comment.setText(rs.getString("comment_text"));
                    comment.setAuthor(rs.getString("author"));
                    Timestamp ts = rs.getTimestamp("created_at");
                    if (ts != null) comment.setCreatedAt(ts.toLocalDateTime());
                    comment.setPostId(post.getId());
                    post.getComments().add(comment);
                    commentIds.add(commentId);
                }
                // Тег
                String tagName = rs.getString("tag_name");
                if (tagName != null && !tagNames.contains(tagName)) {
                    post.getTags().add(tagName);
                    tagNames.add(tagName);
                }
            }
            return post;
        }, id);
    }

    @Override
    public void likePost(Long id, boolean isLike) {
        int likesCount = jdbcTemplate.queryForObject("SELECT likesCount FROM post WHERE id = ?",
                Integer.class,
                id);
        if (isLike) {
            likesCount += 1;
        } else {
            likesCount = (likesCount > 0) ? likesCount - 1 : 0;
        }
        jdbcTemplate.update("UPDATE post SET likesCount = ? WHERE id = ?",
                likesCount,
                id);
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
