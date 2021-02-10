package ua.servlet.restaurant.dao.impl;

import ua.servlet.restaurant.dao.DishesDao;
import ua.servlet.restaurant.dao.mapper.DishesMapper;
import ua.servlet.restaurant.dao.entity.Dishes;
import ua.servlet.restaurant.utils.Prop;

import java.sql.*;
import java.util.*;

public class JDBCDishesDao implements DishesDao {
    private final Connection connection;
    public JDBCDishesDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Dishes> create(Dishes entity) {
        return Optional.empty();
    }

    @Override
    public Dishes findById(int id) {
        return null;
    }

    @Override
    public List<Dishes> findAll() {
        Map<Long, Dishes> users = new HashMap<>();

        final String query = Prop.getDBProperty("select.all.dishes");
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);

            DishesMapper dishesMapper = new DishesMapper();
            while (rs.next()) {
                Dishes dish = dishesMapper.extractFromResultSet(rs);
                dish = dishesMapper.makeUnique(users, dish);
            }
            return new ArrayList<>(users.values());
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(Prop.getDBProperty("select.all.dishes.dbe"));
            return null;
        }
    }

    @Override
    public void update(Dishes entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close()  {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
