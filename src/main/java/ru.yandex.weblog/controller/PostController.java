package ru.yandex.weblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yandex.weblog.model.Paging;
import ru.yandex.weblog.model.Post;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

//    private final PostService postService;
//
//    public PostController(PostService postService) {
//        this.postService = postService;
//    }

    @GetMapping
    public String getPosts(@RequestParam(value = "search", defaultValue = "") String search,
                           @RequestParam(value = "pageSize", defaultValue = "3") int pageSize,
                           @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                           Model model) {

        Post post1 = new Post();
        post1.setId(1L);
        post1.setTitle("Title 1");
        post1.setTextPreview("Пост 1");
        post1.setComments(List.of());

        Post post2 = new Post();
        post2.setId(2L);
        post2.setTitle("Title 2");
        post2.setTextPreview("Пост 2");
        post2.setComments(List.of());

        Post post3 = new Post();
        post3.setId(3L);
        post3.setTitle("Title 3");
        post3.setTextPreview("Пост 3");
        post3.setComments(List.of());

        List<Post> allPosts = List.of(post1, post2, post3);

        int totalPosts = allPosts.size();
        int fromIndex = Math.max(0, (pageNumber - 1) * pageSize);
        int toIndex = Math.min(fromIndex + pageSize, totalPosts);

        List<Post> paginatedPosts = fromIndex >= totalPosts ? List.of() : allPosts.subList(fromIndex, toIndex);

        boolean hasNext = toIndex < totalPosts;
        boolean hasPrevious = pageNumber > 1;

        // 📦 Модель
        model.addAttribute("posts", paginatedPosts);

        Paging paging = new Paging();
        paging.setPageSize(pageSize);
        paging.setPageNumber(pageNumber);
        paging.setHasNext(toIndex < totalPosts);
        paging.setHasPrevious(pageNumber > 1);

        model.addAttribute("paging", paging);

        return "posts";
    }

} 