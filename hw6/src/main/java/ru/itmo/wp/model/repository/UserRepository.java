package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Element;
import ru.itmo.wp.model.domain.User;

import java.util.List;

public interface UserRepository {
    Element find(long id);

    Element findByLogin(String login);

    Element findByEmail(String email);

    Element findByLoginAndPasswordSha(String login, String passwordSha);

    Element findByEmailAndPasswordSha(String email, String passwordSha);

    Element findByLoginOrEmailAndPasswordSha(String loginOrEmail, String passwordSha);

    int findCount();

    List<User> findAll();

    void save(User user, String passwordSha);
}
