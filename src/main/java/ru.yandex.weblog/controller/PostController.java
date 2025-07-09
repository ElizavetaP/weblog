package ru.yandex.weblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yandex.weblog.model.Paging;
import ru.yandex.weblog.model.Post;
import ru.yandex.weblog.service.PostService;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public String getPosts(@RequestParam(value = "search", defaultValue = "") String search,
                           @RequestParam(value = "pageSize", defaultValue = "3") int pageSize,
                           @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                           Model model) {

        List<Post> allPosts = postService.findAll();

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

    @GetMapping("/{id}")
    public String getPost(@PathVariable("id") Long id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "post";
    }

    @PostMapping("/{id}/like")
    public String likePost(@PathVariable("id") Long id, @RequestParam("like") boolean like) {
        postService.likePost(id, like);
        return "redirect:/posts/" + id;
    }

} 