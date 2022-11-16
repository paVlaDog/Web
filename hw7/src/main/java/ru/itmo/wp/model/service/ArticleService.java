package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class ArticleService {

    private final ArticleRepository articleRepository = new ArticleRepositoryImpl();

    public void validateCreateArticle(Article article) throws ValidationException {
        if (Strings.isNullOrEmpty(article.getTitle())) {
            throw new ValidationException("Title is required");
        }
        if (Strings.isNullOrEmpty(article.getText())) {
            throw new ValidationException("Text is required");
        }
        if (article.getTitle().length() > 128) {
            throw new ValidationException("Title is too long (max 128)");
        }
        if (article.getText().length() > 1024) {
            throw new ValidationException("Text is too long (max 1024)");
        }
    }

    public void createArticle(Article article) {
        articleRepository.save(article);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public void changeHiddenById(long id, boolean hide) {
        articleRepository.changeHiddenById(id, hide);
    }

    public List<Article> findByUserId(long userId) {
        return articleRepository.findByUserId(userId);
    }

    public Article find(long id) {
        return articleRepository.find(id);
    }
}
