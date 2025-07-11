package ru.yandex.weblog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.yandex.weblog.configuration.DataSourceConfiguration;
import ru.yandex.weblog.model.Post;
import ru.yandex.weblog.repository.PostRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = {DataSourceConfiguration.class, JdbcPostRepositoryTest.class})
@ComponentScan(basePackages = "ru.yandex.weblog.repository")
@TestPropertySource(locations = "classpath:test-application.properties")
public class JdbcPostRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PostRepository postRepository;

    @Value("${test-sql.path}")
    private String testSqlPath;

    @BeforeEach
    void setUp() throws IOException {
        // Очистка и заполнение тестовых данных в базе
        String sql = Files.readString(Paths.get(testSqlPath));
        jdbcTemplate.execute(sql);
    }

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
