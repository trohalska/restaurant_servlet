package ua.servlet.restaurant.dao.impl;

import ua.servlet.restaurant.dao.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {
    private final DataSource dataSource = ConnectionPoolHolder.getDataSource();

    @Override
    public LoginsDao createLoginsDao() {
        return new JDBCLoginsDao(getConnection());
    }
    @Override
    public CategoriesDao createCategoriesDao() {
        return new JDBCCategoriesDao(getConnection());
    }
    @Override
    public DishesDao createDishesDao() {
        return new JDBCDishesDao(getConnection());
    }
    @Override
    public BasketsDao createBasketsDao() {
        return new JDBCBasketsDao(getConnection());
    }
    @Override
    public OrdersDao createOrdersDao() {
        return new JDBCOrdersDao(getConnection());
    }

    private Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
