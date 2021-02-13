package ua.servlet.restaurant.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.servlet.restaurant.dao.BasketsDao;
import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.DishesDao;
import ua.servlet.restaurant.dao.entity.Baskets;
import ua.servlet.restaurant.dao.entity.Dishes;
import ua.servlet.restaurant.dao.mapper.DishesMapper;
import ua.servlet.restaurant.utils.Prop;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class JDBCBasketsDao implements BasketsDao {
    Logger log = LogManager.getLogger(JDBCBasketsDao.class);
    private final Connection connection;
    public JDBCBasketsDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Baskets> create(Baskets entity) throws DBException {
        return Optional.empty();
    }

    @Override
    public Baskets findById(int id) {
        return null;
    }

    @Override
    public List<Baskets> findAll() throws DBException {
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

            String errorMsg = Prop.getDBProperty("select.all.dishes.dbe");
            log.error(errorMsg);
            throw new DBException(errorMsg);
        }
    }

    @Override
    public void update(Baskets entity) {

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
