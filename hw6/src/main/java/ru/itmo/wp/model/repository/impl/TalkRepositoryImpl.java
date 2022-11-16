package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.*;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.EventRepository;
import ru.itmo.wp.model.repository.TalkRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@SuppressWarnings("SqlNoDataSourceInspection")
public class TalkRepositoryImpl extends ElementRepository implements TalkRepository {
    private final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    @Override
    public Talk find(long id) {
        return castElement(baseFindById(id, "Talks"));
    }

    @Override
    public List<Talk> findBySourceOrTargetUserId(long sourceUserId, long targetUserId)  {
        return castElement(baseFindMany(new LinkedHashSet<>(Arrays.asList(new Pair("long", sourceUserId), new Pair("long", targetUserId))),
                "SELECT * FROM Talks WHERE sourceUserId=? OR targetUserId=? ORDER BY id"));
    }

    @Override
    public void save(Talk talk) {
        baseSave(talk, new LinkedHashSet<>(Arrays.asList(
                new Pair("long", talk.getSourceUserId()),
                new Pair("long", talk.getTargetUserId()),
                new Pair("string", talk.getText())
        )), "INSERT INTO `Talks` (`sourceUserId`, `targetUserId`, `text`, `creationTime`) VALUES (?, ?, ?, NOW())");
    }

    protected Element toElementImpl(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        Talk talk = new Talk();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    talk.setId(resultSet.getLong(i));
                    break;
                case "sourceUserId":
                    talk.setSourceUserId(resultSet.getLong(i));
                    break;
                case "targetUserId":
                    talk.setTargetUserId(resultSet.getLong(i));
                    break;
                case "text":
                    talk.setText(resultSet.getString(i));
                    break;
                case "creationTime":
                    talk.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }
        return talk;
    }

    private Talk castElement(Element el) {
        return (Talk)el;
    }

    private List<Talk> castElement(List<Element> listEl) {
        List<Talk> talks = new ArrayList<>();
        for (Element e : listEl) {
            talks.add((Talk)e);
        }
        return talks;
    }
}
