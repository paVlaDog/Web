package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.EventType;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.repository.impl.TalkRepositoryImpl;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused"})
public class TalksPage extends Page {
    UserService userService = new UserService();
    TalkRepository talkRepository = new TalkRepositoryImpl();
    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        checkLoggedUser(request);
        view.put("users", userService.findAll());
        view.put("talks", talkRepository.findBySourceOrTargetUserId(getUser().getId(), getUser().getId()));
    }

    private void message(HttpServletRequest request, Map<String, Object> view) {
        checkLoggedUser(request);

        Talk talk = new Talk();
        talk.setSourceUserId(getUser().getId());
        talk.setTargetUserId(Long.parseLong(request.getParameter("targetUserId")));
        talk.setText(request.getParameter("text"));
        talkRepository.save(talk);
        setMessage("You send message!");
        view.put("talks", talkRepository.findBySourceOrTargetUserId(getUser().getId(), getUser().getId()));
        throw new RedirectException("/talks");
    }

    private void checkLoggedUser(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") == null) {
            setMessage("You aren't authorized");
            throw new RedirectException("/index");
        }
    }
}