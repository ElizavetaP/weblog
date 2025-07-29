package ru.yandex.weblog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.weblog.model.Post;
import ru.yandex.weblog.repository.PostRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = "classpath:test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@TestPropertySource(locations = "classpath:test-application.properties")
public class JdbcPostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Value("${test-sql.path}")
    private String testSqlPath;

    @Test
    void save_shouldAddUserToDatabase() {
        Post post = new Post();
        post.setId(3L);
        post.setTitle("Кот");
        post.setTextPreview("Текст 3");
        post.setImage("3.jpg");

        postRepository.save(post);

        Post savedPost = postRepository.findAll("", 5, 1).stream()
                .filter(createdPost -> createdPost.getId().equals(3L))
                .findFirst()
                .orElse(null);

        assertNotNull(savedPost);
        assertEquals("Кот", savedPost.getTitle());
        assertEquals("Текст 3", savedPost.getTextPreview());
    }

    @Test
    void findAll_shouldReturnAllPosts() {
        List<Post> posts = postRepository.findAll("", 10, 1);

        assertNotNull(posts);
        assertEquals(2, posts.size());

        Post post = posts.getFirst();
        assertEquals(1L, post.getId());
        assertEquals("Содержимое 1", post.getTextPreview());
    }

    @Test
    void deleteById_shouldRemovePostFromDatabase() {
        postRepository.deleteById(1L);

        List<Post> posts = postRepository.findAll("", 10, 1);

        Post deletedPost = posts.stream()
                .filter(post -> post.getId().equals(1L))
                .findFirst()
                .orElse(null);
        assertNull(deletedPost);
    }
}
