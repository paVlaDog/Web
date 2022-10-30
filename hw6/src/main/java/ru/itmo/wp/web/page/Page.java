package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class Page {
    HttpServletRequest request;

    protected void action(HttpServletRequest request, Map<String, Object> view) {
    }

    protected void before(HttpServletRequest request, Map<String, Object> view) {
        this.request = request;
        UserService userService = new UserService();
        view.put("userCount", userService.findCount());
        putUser(request, view);
        putMessage(request, view);
    }

    protected void after(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }

    private void putUser(HttpServletRequest request, Map<String, Object> view) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            view.put("user", user);
        }
    }

    private void putMessage(HttpServletRequest request, Map<String, Object> view) {
        String message = (String) request.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            request.getSession().removeAttribute("message");
        }
    }

    protected void setMessage(String message) {
        request.getSession().setAttribute("message", message);
    }

    protected void setUser(User user) {
        request.getSession().setAttribute("user", user);
    }

    protected User getUser() {
        return (User)request.getSession().getAttribute("user");
    }
}

