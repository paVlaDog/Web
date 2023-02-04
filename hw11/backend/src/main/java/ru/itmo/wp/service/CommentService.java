package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.form.CommentCredentials;
import ru.itmo.wp.form.PostCredentials;
import ru.itmo.wp.repository.CommentRepository;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Comment create(CommentCredentials commentCredentials) {
        Comment comment = new Comment();
        comment.setPost(commentCredentials.getPost());
        comment.setText(commentCredentials.getText());
        comment.setUser(commentCredentials.getUser());
        commentRepository.save(comment);
        return comment;
    }
}
