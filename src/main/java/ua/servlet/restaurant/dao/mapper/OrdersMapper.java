package ua.servlet.restaurant.dao.mapper;

import ua.servlet.restaurant.dao.entity.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class OrdersMapper implements ObjectMappers<Orders> {

    @Override
    public Orders extractFromResultSet(ResultSet rs) throws SQLException {
        return Orders.builder()
                .id(rs.getLong("id"))
                .status(Status.valueOf(rs.getString("status")))
                .time(rs.getTimestamp("time").toLocalDateTime())
                .totalPrice(rs.getBigDecimal("total_price"))
                .login(Logins.builder()
                        .login(rs.getString("login"))
                        .build())
                .build();
    }

    @Override
    public Orders makeUnique(Map<Long, Orders> cache, Orders orders) {
        cache.putIfAbsent(orders.getId(), orders);
        return cache.get(orders.getId());
    }
}
