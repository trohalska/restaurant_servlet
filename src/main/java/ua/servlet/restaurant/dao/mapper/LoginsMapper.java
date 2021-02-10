package ua.servlet.restaurant.dao.mapper;

import ua.servlet.restaurant.dao.entity.Logins;
import ua.servlet.restaurant.dao.entity.RoleType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class LoginsMapper implements ObjectMappers<Logins> {

    @Override
    public Logins extractFromResultSet(ResultSet rs) throws SQLException {
        return Logins.builder()
                .id(rs.getLong("id"))
                .login(rs.getString("login"))
                .password(rs.getString("password"))
                .email(rs.getString("email"))
                .role(RoleType.valueOf(rs.getString("role")))
                .build();
    }

    @Override
    public Logins makeUnique(Map<Long, Logins> cache, Logins user) {
        cache.putIfAbsent(user.getId(), user);
        return cache.get(user.getId());
    }
}
