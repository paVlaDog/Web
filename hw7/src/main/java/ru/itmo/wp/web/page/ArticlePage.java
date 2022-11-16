package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.EventType;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.EventRepository;
import ru.itmo.wp.model.repository.impl.EventRepositoryImpl;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ArticlePage extends Page {
    private final ArticleService articleService = new ArticleService();

    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        checkLoggedUser(request);
    }

    protected void createArticle(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        checkLoggedUser(request);
        Article article = new Article();
        article.setUserId(getUser().getId());
        article.setTitle(request.getParameter("title"));
        article.setText(request.getParameter("text"));
        article.setHidden(request.getParameter("hidden").equals("hidden"));

        articleService.validateCreateArticle(article);
        articleService.createArticle(article);

        setMessage("You create new article - " + article.getTitle() + "!");
        throw new RedirectException("/index");
    }
}
