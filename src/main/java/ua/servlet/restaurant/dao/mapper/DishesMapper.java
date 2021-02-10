package ua.servlet.restaurant.dao.mapper;

import ua.servlet.restaurant.dao.entity.Categories;
import ua.servlet.restaurant.dao.entity.Dishes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

// TODO select lang

public class DishesMapper implements ObjectMappers<Dishes> {

    @Override
    public Dishes extractFromResultSet(ResultSet rs) throws SQLException {
        return Dishes.builder()
                .id(rs.getLong("id"))
                .nameEn(rs.getString("name_en"))
//                .nameUa(rs.getString("name_ua"))
                .price(rs.getBigDecimal("price"))
                .category(Categories.builder()
                        .id(rs.getLong("category_Id"))
                        .categoryEn(rs.getString("category_en"))
                        .categoryUa(rs.getString("category_ua"))
                        .build())
                .build();
    }

    @Override
    public Dishes makeUnique(Map<Long, Dishes> cache, Dishes dishes) {
        cache.putIfAbsent(dishes.getId(), dishes);
        return cache.get(dishes.getId());
    }
}
