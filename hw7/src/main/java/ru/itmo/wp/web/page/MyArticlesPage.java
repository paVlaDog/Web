package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.annotation.Json;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class MyArticlesPage extends Page {
    ArticleService articleService = new ArticleService();

    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        checkLoggedUser(request);
        view.put("posts", articleService.findByUserId(getUser().getId()));
    }

    @Json
    protected void hidePost(HttpServletRequest request, Map<String, Object> view) {
        try {
            long postId = Long.parseLong(request.getParameter("postId"));
            long userId = articleService.find(postId).getUserId();
            checkUser(request, userId);
            articleService.changeHiddenById(postId, true);
        } catch (NumberFormatException e) {
            setMessage("You are hacker");
            throw new RedirectException("/index");
        }
    }

    @Json
    protected void showPost(HttpServletRequest request, Map<String, Object> view) {
        try {
            long postId = Long.parseLong(request.getParameter("postId"));
            long userId = articleService.find(postId).getUserId();
            checkUser(request, userId);
            articleService.changeHiddenById(postId, false);
        } catch (NumberFormatException e) {
            setMessage("You are hacker");
            throw new RedirectException("/index");
        }

    }
}
