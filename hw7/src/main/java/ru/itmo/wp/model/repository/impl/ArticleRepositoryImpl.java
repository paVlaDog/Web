package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.Element;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.TalkRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

@SuppressWarnings("SqlNoDataSourceInspection")
public class ArticleRepositoryImpl extends ElementRepository implements ArticleRepository {
    private final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();
    private final String SELECT_SQL = "SELECT * FROM Article ";

    @Override
    public Article find(long id) {
        return castElement(baseFindById(id, "Article"));
    }

    @Override
    public List<Article> findAll()  {
        return castElement(baseFindMany(new LinkedHashSet<>(), SELECT_SQL + "ORDER BY creationTime"));
    }

    @Override
    public List<Article> findByUserId(long userId)  {
        return castElement(baseFindMany(new LinkedHashSet<>(Arrays.asList(new Pair("long", userId))),
                "SELECT * FROM Article WHERE userId=? ORDER BY id"));
    }

    @Override
    public void changeHiddenById(long id, boolean hide) {
        baseChange(new LinkedHashSet<>(Arrays.asList(new Pair("boolean", hide), new Pair("long", id))),
                "UPDATE Article SET hidden=? WHERE id=?");
    }

    @Override
    public void save(Article article) {
        baseSave(article, new LinkedHashSet<>(Arrays.asList(
                new Pair("long", article.getUserId()),
                new Pair("string", article.getTitle()),
                new Pair("string", article.getText()),
                new Pair("boolean", article.isHidden())
        )), "INSERT INTO `Article` (`userId`, `title`, `text`, `hidden`, `creationTime`) VALUES (?, ?, ?, ?, NOW())");
    }

    protected Element toElementImpl(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        Article article = new Article();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    article.setId(resultSet.getLong(i));
                    break;
                case "userId":
                    article.setUserId(resultSet.getLong(i));
                    break;
                case "title":
                    article.setTitle(resultSet.getString(i));
                    break;
                case "text":
                    article.setText(resultSet.getString(i));
                    break;
                case "hidden":
                    article.setHidden(resultSet.getBoolean(i));
                    break;
                case "creationTime":
                    article.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }
        return article;
    }

    private Article castElement(Element el) {
        return (Article)el;
    }

    private List<Article> castElement(List<Element> listEl) {
        List<Article> articles = new ArrayList<>();
        for (Element e : listEl) {
            articles.add((Article)e);
        }
        return articles;
    }
}
