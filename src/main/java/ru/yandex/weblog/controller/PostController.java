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
    public String getPosts(@RequestParam(value = "search", defaultValue = "") String tag,
                           @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
                           @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                           Model model) {

        List<Post> paginatedPosts = postService.findAll(tag, pageSize, pageNumber);

        boolean hasNext = paginatedPosts.size() == pageSize;
        boolean hasPrevious = pageNumber > 1;

        model.addAttribute("posts", paginatedPosts);

        Paging paging = new Paging();
        paging.setPageSize(pageSize);
        paging.setPageNumber(pageNumber);
        paging.setHasNext(hasNext);
        paging.setHasPrevious(hasPrevious);

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

    @GetMapping("/add")
    public String addPostForm(Model model) {
        model.addAttribute("post", null);
        return "add-post";
    }

    @PostMapping
    public String addPost(@RequestParam("title") String title,
                          @RequestParam("textPreview") String textPreview,
                          @RequestParam(value = "tags", required = true) String tags,
                          @RequestParam(value = "image", required = false) String image) {

        Post post = new Post();
        post.setTitle(title);
        post.setTextPreview(textPreview);
        post.setImage(image);

        postService.addPost(post);

        return "redirect:/posts";
    }

    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable("id") Long id) {
        postService.deleteById(id);
        return "redirect:/posts";
    }

    @GetMapping("/{id}/edit")
    public String editPostForm(@PathVariable("id") Long id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "add-post";
    }

    @PostMapping("/{id}")
    public String editPost(@PathVariable("id") Long id,
                           @RequestParam("title") String title,
                           @RequestParam("textPreview") String textPreview,
                           @RequestParam(value = "tags", required = true) String tags,
                           @RequestParam(value = "image", required = false) String image) {
        Post post = new Post();
        post.setId(id);
        post.setTitle(title);
        post.setTextPreview(textPreview);
        System.out.println(image);
        post.setImage(image);
        postService.editPost(post);
        return "redirect:/posts/" + id;
    }
} 