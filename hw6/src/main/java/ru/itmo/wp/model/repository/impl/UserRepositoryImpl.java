package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.Element;
import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@SuppressWarnings("SqlNoDataSourceInspection")
public class UserRepositoryImpl extends ElementRepository implements UserRepository {
    private final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();
    private final String SELECT_SQL = "SELECT * FROM User ";

    @Override
    public User find(long id) {
        return castElement(baseFindById(id, "User"));
    }

    @Override
    public User findByLogin(String login)  {
        return castElement(baseFindOne(new LinkedHashSet<>(Arrays.asList(new Pair("string", login))),
                SELECT_SQL + "WHERE login=?"));
    }

    @Override
    public User findByEmail(String email)  {
        return castElement(baseFindOne(new LinkedHashSet<Pair>(Arrays.asList(new Pair("string", email))),
                SELECT_SQL + "WHERE email=?"));
    }

    @Override
    public User findByLoginAndPasswordSha(String login, String passwordSha)  {
        return castElement(baseFindOne(new LinkedHashSet<Pair>(Arrays.asList(new Pair("string", login),
                        new Pair("string", passwordSha))), SELECT_SQL + "WHERE login=? AND passwordSha=?"));
    }

    @Override
    public User findByEmailAndPasswordSha(String email, String passwordSha)  {
        return castElement(baseFindOne(new LinkedHashSet<Pair>(Arrays.asList(new Pair("string", email),
                        new Pair("string", passwordSha))), SELECT_SQL + "WHERE email=? AND passwordSha=?"));
    }

    @Override
    public User findByLoginOrEmailAndPasswordSha(String loginOrEmail, String passwordSha)  {
        if (loginOrEmail.contains("@")) {
            return castElement(findByEmailAndPasswordSha(loginOrEmail, passwordSha));
        } else {
            return castElement(findByLoginAndPasswordSha(loginOrEmail, passwordSha));
        }
    }


    @Override
    public int findCount() {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM User")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    resultSet.next();
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.", e);
        }
    }

    @Override
    public List<User> findAll()  {
        return castElement(baseFindMany(new LinkedHashSet<>(), SELECT_SQL + "ORDER BY id DESC"));
    }

    protected Element toElementImpl(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        User user = new User();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    user.setId(resultSet.getLong(i));
                    break;
                case "login":
                    user.setLogin(resultSet.getString(i));
                    break;
                case "email":
                    user.setEmail(resultSet.getString(i));
                    break;
                case "creationTime":
                    user.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }
        return user;
    }


    @Override
    public void save(User user, String passwordSha) {
        baseSave(user, new LinkedHashSet<>(Arrays.asList(
                new Pair("string", user.getLogin()),
                new Pair("string", passwordSha),
                new Pair("string", user.getEmail())
        )), "INSERT INTO `User` (`login`, `passwordSha`, `email`, `creationTime`) VALUES (?, ?, ?, NOW())");
    }

    private User castElement(Element el) {
        return (User)el;
    }

    private List<User> castElement(List<Element> listEl) {
        List<User> users = new ArrayList<>();
        for (Element e : listEl) {
            users.add((User)e);
        }
        return users;
    }
}
