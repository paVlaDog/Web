package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itmo.wp.service.UserService;

@Controller
public class UserPage extends Page {
    private final UserService userService;

    public UserPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String user(Model model) {
        return "UserPage";
    }

    @GetMapping("/user/{id}")
    public String users(@PathVariable String id, Model model) {
        try {
            if (!id.isEmpty()) {
                model.addAttribute("user", userService.findById(Long.parseLong(id)));
            }
        } catch (NumberFormatException e) {
            //
        }
        return "UserPage";
    }
}
