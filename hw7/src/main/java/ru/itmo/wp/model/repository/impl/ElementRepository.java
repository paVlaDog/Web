package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.Element;
import ru.itmo.wp.model.exception.RepositoryException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public abstract class ElementRepository {
    private final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    protected static class Pair {
        public String first;
        public Object second;

        public Pair(String first, Object second) {
            this.first = first;
            this.second = second;
        }
    }

    protected Element baseFindById (long id, String tableName) {
        return baseFindOne(new LinkedHashSet<>(Arrays.asList(new Pair("long", id))),
                "SELECT * FROM " + tableName + " WHERE id=?");
    }

    public Element baseFindOne(Set<Pair> parameters, String sql) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                setParametersStatement(parameters, statement);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return toElement(statement.getMetaData(), resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find Element.", e);
        }
    }

    public List<Element> baseFindMany(Set<Pair> parameters, String sql) {
        List<Element> elements = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                setParametersStatement(parameters, statement);
                try (ResultSet resultSet = statement.executeQuery()) {
                    Element element;
                    while ((element = toElement(statement.getMetaData(), resultSet)) != null) {
                        elements.add(element);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find Element.", e);
        }
        return elements;
    }

    public void baseChange(Set<Pair> parameters, String sql) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                setParametersStatement(parameters, statement);
                ResultSet resultSet = statement.executeQuery();
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find Element.", e);
        }
    }

    public void baseSave(Element element, Set<Pair> parameters, String sql) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                setParametersStatement(parameters, statement);
                if (statement.executeUpdate() != 1) {
                    throw new RepositoryException("Can't save Element.");
                } else {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        element.setId(generatedKeys.getLong(1));
                        element.setCreationTime(find(element.getId()).getCreationTime());
                    } else {
                        throw new RepositoryException("Can't save Element [no autogenerated fields].");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save Element.", e);
        }
    }

    private void setParametersStatement(Set<Pair> parameters, PreparedStatement statement) throws SQLException {
        int i = 1;
        for (Pair elem : parameters) {
            switch (elem.first) {
                case "long":
                    statement.setLong(i, (Long)elem.second);
                    break;
                case "string":
                    statement.setString(i, (String)elem.second);
                    break;
                case "boolean":
                    statement.setBoolean(i, (Boolean) elem.second);
                    break;
                default:
                    // No operations.
            }
            i++;
        }
    }

    protected Element toElement(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }
        return toElementImpl(metaData, resultSet);
    }

    protected abstract Element toElementImpl(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException;

    protected abstract Element find(long id) throws SQLException;
}