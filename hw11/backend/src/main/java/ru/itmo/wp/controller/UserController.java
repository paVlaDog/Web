package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.form.FullUserCredentials;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.form.validator.FullUserCredentialsEnterValidator;
import ru.itmo.wp.form.validator.UserCredentialsEnterValidator;
import ru.itmo.wp.service.JwtService;
import ru.itmo.wp.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/1")
public class UserController {
    private final JwtService jwtService;
    private final UserService userService;
    private final FullUserCredentialsEnterValidator fullUserCredentialsEnterValidator;


    public UserController(JwtService jwtService, UserService userService, FullUserCredentialsEnterValidator fullUserCredentialsEnterValidator) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.fullUserCredentialsEnterValidator = fullUserCredentialsEnterValidator;
    }

    @GetMapping("users/auth")
    public User findUserByJwt(@RequestParam String jwt) {
        return jwtService.find(jwt);
    }

    @GetMapping("users")
    public List<User> findUsers() {
        return userService.findAll();
    }

    @InitBinder("userCredentials")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(fullUserCredentialsEnterValidator);
    }

    @PostMapping("users")
    public String create(@RequestBody @Valid FullUserCredentials fullUserCredentials, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
        userService.register(fullUserCredentials);
        User user = userService.findByLogin(fullUserCredentials.getLogin());
        return jwtService.create(user);
    }
}
