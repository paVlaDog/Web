package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.*;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.EventRepository;
import ru.itmo.wp.model.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@SuppressWarnings("SqlNoDataSourceInspection")
public class EventRepositoryImpl extends ElementRepository implements EventRepository {
    private final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    @Override
    public Event find(long id) {
        return castElement(baseFindById(id, "Event"));
    }


    @Override
    public void save(Event event) {
        baseSave(event, new LinkedHashSet<>(Arrays.asList(
                new Pair("long", event.getUserId()),
                new Pair("string", event.getType().toString())
        )), "INSERT INTO `Event` (`userId`, `type`, `creationTime`) VALUES (?, ?, NOW())");
    }


    protected Element toElementImpl(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        Event event = new Event();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    event.setId(resultSet.getLong(i));
                    break;
                case "userId":
                    event.setUserId(resultSet.getLong(i));
                    break;
                case "type":
                    event.setType(EventType.valueOf(resultSet.getString(i)));
                    break;
                case "creationTime":
                    event.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }
        return event;
    }

    private Event castElement(Element el) {
        return (Event)el;
    }

    private List<Event> castElement(List<Element> listEl) {
        List<Event> events = new ArrayList<>();
        for (Element e : listEl) {
            events.add((Event)e);
        }
        return events;
    }
}
