package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Notice;
import ru.itmo.wp.form.NoticeCredentials;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.form.validator.NoticeCredentialsAddValidator;
import ru.itmo.wp.form.validator.UserCredentialsEnterValidator;
import ru.itmo.wp.service.NoticeService;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class NoticePage extends Page {
    private final NoticeService noticeService;
//    private final NoticeCredentialsAddValidator noticeCredentialsAddValidator;

    public NoticePage(NoticeService noticeService) {
        this.noticeService = noticeService;
//        this.noticeCredentialsAddValidator = noticeCredentialsAddValidator;
    }

//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        binder.addValidators(noticeCredentialsAddValidator);
//    }

    @GetMapping("/notice")
    public String addNotice(Model model) {
        model.addAttribute("noticeForm", new NoticeCredentials());
        return "NoticePage";
    }

    @PostMapping("/notice")
    public String addNotice(@Valid @ModelAttribute("noticeForm") NoticeCredentials noticeForm,
                           BindingResult bindingResult,
                           HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "NoticePage";
        }

        Notice notice = noticeService.addNotice(noticeForm);
        setMessage(httpSession, "Add notice, " + notice.getContent());

        return "redirect:";
    }
}
