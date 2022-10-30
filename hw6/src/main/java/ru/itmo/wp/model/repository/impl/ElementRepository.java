package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.Element;
import ru.itmo.wp.model.exception.RepositoryException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class ElementRepository {
    private final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    protected class Pair {
        public String first;
        public Object second;

        public Pair(String first, Object second) {
            this.first = first;
            this.second = second;
        }
    }

    public Element baseFindOne(Set<UserRepositoryImpl.Pair> parameters, String sql) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                int i = 1;
                for (UserRepositoryImpl.Pair elem : parameters) {
                    switch (elem.first) {
                        case "long":
                            statement.setLong(i, (Long)elem.second);
                            break;
                        case "string":
                            statement.setString(i, (String)elem.second);
                            break;
                        default:
                            // No operations.
                    }
                    i++;
                }
                try (ResultSet resultSet = statement.executeQuery()) {
                    return toElement(statement.getMetaData(), resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.", e);
        }
    }

    public List<Element> baseFindMany(Set<UserRepositoryImpl.Pair> parameters, String sql) {
        List<Element> elements = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    int i = 1;
                    for (UserRepositoryImpl.Pair elem : parameters) {
                        switch (elem.first) {
                            case "long":
                                statement.setLong(i, (Long)elem.second);
                                break;
                            case "string":
                                statement.setString(i, (String)elem.second);
                                break;
                            default:
                                // No operations.
                        }
                        i++;
                    }
                    Element element;
                    while ((element = toElement(statement.getMetaData(), resultSet)) != null) {
                        elements.add(element);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.", e);
        }
        return elements;
    }

    protected abstract Element toElement(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException;
}
