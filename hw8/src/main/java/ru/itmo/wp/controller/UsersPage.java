package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.StatusCredentials;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UsersPage extends Page {
    private final UserService userService;

    public UsersPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/all")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "UsersPage";
    }

    @PostMapping("/users/all")
    public String changeDisable(@Valid @ModelAttribute("statusForm") StatusCredentials statusForm,
                               BindingResult bindingResult,
                               HttpSession httpSession, Model model) {
        if (bindingResult.hasErrors()) {
            return "UsersPage";
        }

        model.addAttribute("users", userService.findAll());
        try {
            long usrId = Long.parseLong(statusForm.getUserId());
            if ((statusForm.getStatus().equals("block") || statusForm.getStatus().equals("unBlock"))
                    && userService.findById(usrId) != null) {
                User user = userService.updateStatus(statusForm);
                setMessage(httpSession, user.getLogin() + " status change");
                return "redirect:/users/all";
            }
        } catch (NumberFormatException e) {
            //
        }


        return "UsersPage";
    }


}
