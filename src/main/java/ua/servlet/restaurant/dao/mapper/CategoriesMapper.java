package ua.servlet.restaurant.dao.mapper;

import ua.servlet.restaurant.dao.entity.Categories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class CategoriesMapper implements ObjectMappers<Categories> {

    @Override
    public Categories extractFromResultSet(ResultSet rs) throws SQLException {
        return Categories.builder()
                .id(rs.getLong("id"))
                .categoryEn(rs.getString("category_en"))
                .categoryUa(rs.getString("category_ua"))
                .build();
    }

    @Override
    public Categories makeUnique(Map<Long, Categories> cache, Categories categories) {
        cache.putIfAbsent(categories.getId(), categories);
        return cache.get(categories.getId());
    }
}
