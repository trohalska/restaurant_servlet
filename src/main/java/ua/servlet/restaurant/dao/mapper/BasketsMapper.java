package ua.servlet.restaurant.dao.mapper;

import ua.servlet.restaurant.dao.entity.Baskets;
import ua.servlet.restaurant.dao.entity.Categories;
import ua.servlet.restaurant.dao.entity.Dishes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class BasketsMapper implements ObjectMappers<Baskets> {

    @Override
    public Baskets extractFromResultSet(ResultSet rs) throws SQLException {
        return Baskets.builder()
                .id(rs.getLong("id"))
                .dish(Dishes.builder()
                        .id(rs.getLong("dish_id"))
                        .nameEn(rs.getString("name_en"))
                        .nameUa(rs.getString("name_ua"))
                        .price(rs.getBigDecimal("price"))
                        .category(Categories.builder()
                                .id(rs.getLong("category_id"))
                                .categoryEn(rs.getString("category_en"))
                                .categoryUa(rs.getString("category_ua"))
                                .build())
                        .build())
                .build();
    }

    @Override
    public Baskets makeUnique(Map<Long, Baskets> cache, Baskets baskets) {
        cache.putIfAbsent(baskets.getId(), baskets);
        return cache.get(baskets.getId());
    }
}
