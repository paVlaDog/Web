package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;

import java.util.List;

public interface ArticleRepository {
    Article find(long id);
    List<Article> findAll();
    List<Article> findByUserId(long userId);
    void changeHiddenById(long id, boolean hide);
    void save(Article article);
}
