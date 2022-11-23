package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.NoticeCredentials;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.service.NoticeService;
import ru.itmo.wp.service.UserService;

@Component
public class NoticeCredentialsAddValidator implements Validator {
    private final NoticeService noticeService;

    public NoticeCredentialsAddValidator(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    public boolean supports(Class<?> clazz) {
        return NoticeCredentials.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
//        if (!errors.hasErrors()) {
//            NoticeCredentials noticeForm = (NoticeCredentials) target;
//            if (noticeService.findByContent(noticeForm.getContent()) == null) {
//                errors.rejectValue(
//                        "content", "content.invalid-content", "Invalid content");
//            }
//        }
    }
}
