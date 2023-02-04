package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.FullUserCredentials;
import ru.itmo.wp.form.PostCredentials;
import ru.itmo.wp.repository.PostRepository;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> findAll() {
        return postRepository.findAllByOrderByCreationTimeDesc();
    }

    public Post create(PostCredentials postCredentials) {
        Post post = new Post();
        post.setTitle(postCredentials.getTitle());
        post.setText(postCredentials.getText());
        post.setUser(postCredentials.getUser());
        postRepository.save(post);
        return post;
    }
}
