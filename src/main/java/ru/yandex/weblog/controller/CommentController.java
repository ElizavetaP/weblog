package ru.yandex.weblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.weblog.service.CommentService;

@Controller
@RequestMapping("/posts")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{id}/comments")
    public String addComment(@PathVariable("id") Long id, @RequestParam("text") String text) {
        commentService.addComment(id, text);
        return "redirect:/posts/" + id;
    }

    @PostMapping("/{id}/comments/{commentId}")
    public String editComment(@PathVariable("id") Long id, @PathVariable("commentId") Long commentId,
                              @RequestParam("text") String text) {

        commentService.editTextComment(commentId, text);
        return "redirect:/posts/" + id;
    }

    @PostMapping("/{id}/comments/{commentId}/delete")
    public String deleteComment(@PathVariable("id") Long id, @PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
        return "redirect:/posts/" + id;
    }
}
