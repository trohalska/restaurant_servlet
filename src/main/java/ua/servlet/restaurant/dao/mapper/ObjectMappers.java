package ua.servlet.restaurant.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public interface ObjectMappers<T> {

    T extractFromResultSet(ResultSet rs) throws SQLException;

    T makeUnique(Map<Long, T> cache, T user);
}
