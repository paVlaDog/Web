package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.repository.CommentRepository;
import ru.itmo.wp.repository.PostRepository;
import ru.itmo.wp.security.AnyRole;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.CommentService;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostPage extends Page {
    private final PostService postService;
    private final CommentService commentService;

    public PostPage(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @Guest
    @GetMapping("/post")
    public String posts() {
        return "PostPage";
    }

    @Guest
    @GetMapping("/post/{id}")
    public String posts(@PathVariable String id, Model model) {
        try {
            downloadPostAndComments(id, model);
        } catch (NumberFormatException e) {
            //
        }
        return "PostPage";
    }

    private void downloadPostAndComments(String id, Model model) {
        Post post = postService.findById(Long.parseLong(id));
        model.addAttribute("post", post);
        if (post != null) {
            model.addAttribute("comments", commentService.findByPost(post));
        }
        model.addAttribute("newComment", new Comment());
    }

    @PostMapping("/post/{id}")
    public String writeComment(@Valid @ModelAttribute("post") Comment comment,
                                BindingResult bindingResult,
                                @PathVariable String id,
                                HttpSession httpSession,
                                Model model) {
        if (bindingResult.hasErrors()) {
            downloadPostAndComments(id, model);
            return "PostPage";
        }

        try {
            postService.writeComment(getUser(httpSession), postService.findById(Long.parseLong(id)), comment);
            putMessage(httpSession, "You write new comment");
        } catch (NumberFormatException e) {
            //
        }

        downloadPostAndComments(id, model);

        return "PostPage";
    }
}
