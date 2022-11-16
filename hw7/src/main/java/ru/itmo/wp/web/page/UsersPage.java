package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.annotation.Json;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class UsersPage extends Page {
    private final UserService userService = new UserService();

    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
    }

    private void findUser(HttpServletRequest request, Map<String, Object> view) {
        view.put("user",
                userService.find(Long.parseLong(request.getParameter("userId"))));
    }

    @Json
    private void addAdmin(HttpServletRequest request, Map<String, Object> view) {
        try {
            long userId = Long.parseLong((request.getParameter("userId")));
            checkLoggedUser(request);
            User curUser = userService.find(getUser().getId());
            if (curUser.isAdmin()) {
                userService.changeAdminById(userId, true);
            } else {
                setMessage("You are not correct user");
                throw new RedirectException("/index");
            }
        } catch (NumberFormatException e) {
            setMessage("You are hacker");
            throw new RedirectException("/index");
        }
    }

    @Json
    private void removeAdmin(HttpServletRequest request, Map<String, Object> view) {
        try {
            long userId = Long.parseLong((request.getParameter("userId")));
            checkLoggedUser(request);
            User curUser = userService.find(getUser().getId());
            if (curUser.isAdmin()) {
                userService.changeAdminById(userId, false);
            } else {
                setMessage("You are not correct user");
                throw new RedirectException("/index");
            }
        } catch (NumberFormatException e) {
            setMessage("You are hacker");
            throw new RedirectException("/index");
        }
    }
}
