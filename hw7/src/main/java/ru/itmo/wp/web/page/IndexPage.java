package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused"})
public class IndexPage extends Page {
    ArticleService articleService = new ArticleService();
    UserService userService = new UserService();

    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        view.put("posts", articleService.findAll());
        view.put("users", userService.findAll());
    }
}
