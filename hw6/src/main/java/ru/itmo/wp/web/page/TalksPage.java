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
import java.sql.SQLException;
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
        try {
            long tarUsrId = Long.parseLong(request.getParameter("targetUserId"));
            if (talkRepository.find(tarUsrId) != null && getUser() != null && !request.getParameter("text").trim().isEmpty()) {
                talk.setSourceUserId(getUser().getId());
                talk.setTargetUserId(tarUsrId);
                talk.setText(request.getParameter("text"));
                talkRepository.save(talk);
                setMessage("You send message!");
                view.put("talks", talkRepository.findBySourceOrTargetUserId(getUser().getId(), getUser().getId()));
            }
        } catch (NumberFormatException | NullPointerException e1) {
            //
        }
        throw new RedirectException("/talks");
    }
}