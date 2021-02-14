package ua.servlet.restaurant.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.OrdersDao;
import ua.servlet.restaurant.dao.entity.Orders;
import ua.servlet.restaurant.dao.entity.Status;
import ua.servlet.restaurant.dao.mapper.OrdersMapper;
import ua.servlet.restaurant.utils.Prop;

import java.sql.*;
import java.util.*;

public class JDBCOrdersDao implements OrdersDao {
    Logger log = LogManager.getLogger(JDBCOrdersDao.class);
    private final Connection connection;
    public JDBCOrdersDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Orders> create(Orders entity) throws DBException {
        ResultSet rs;

        final String query = Prop.getDBProperty("create.order");
//        final String query = "INSERT INTO orders (status, time, total_price, login_id) VALUES (?, current_timestamp, " +
//                "(SELECT SUM(d.price) FROM baskets b JOIN dishes d on b.dish_id = d.id WHERE b.login_id=?), ?); " +
//                "DELETE FROM baskets WHERE login_id=?";
        try (PreparedStatement pstmt =
                     connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            int k = 1;
            pstmt.setString(k++, entity.getStatus().name());
            pstmt.setLong(k++, entity.getLogin().getId());
            pstmt.setLong(k++, entity.getLogin().getId());
            pstmt.setLong(k, entity.getLogin().getId());

            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();

                if (rs.next()) {
                    entity.setId(rs.getLong("id"));
                    entity.setTime(rs.getTimestamp("time").toLocalDateTime());
                    entity.setTotalPrice(rs.getBigDecimal("total_price"));
                }
                rs.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();

            String errorMsg = Prop.getDBProperty("create.order.dbe");
            log.error(errorMsg);
            throw new DBException(errorMsg);
        }

        deleteAllFromBasket(entity.getLogin().getId().intValue()); // transaction !!!

        return Optional.of(entity);
    }

    @Override
    public Optional<Orders> findById(int id) throws DBException {
        Optional<Orders> result = Optional.empty();

        final String query = Prop.getDBProperty("select.orders");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            OrdersMapper ordersMapper = new OrdersMapper();
            if (rs.next()) {
                result = Optional.of(ordersMapper.extractFromResultSet(rs));
            }
            rs.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();

            String errorMsg = Prop.getDBProperty("select.orders.dbe");
            log.error(errorMsg);
            throw new DBException(errorMsg);
        }
    }

    @Override
    public List<Orders> findAll() throws DBException {
        return null;
    }

    public Optional<List<Orders>> findAllByLoginId(Long id) throws DBException {
        Map<Long, Orders> orders = new HashMap<>();
        List<Orders> list;

        final String query = Prop.getDBProperty("select.all.orders");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            OrdersMapper ordersMapper = new OrdersMapper();
            while (rs.next()) {
                Orders order = ordersMapper.extractFromResultSet(rs);
                ordersMapper.makeUnique(orders, order);
            }
            rs.close();
            list = new ArrayList<>(orders.values());
            return list.isEmpty() ? Optional.empty() : Optional.of(list);
        } catch (SQLException e) {
            e.printStackTrace();

            String errorMsg = Prop.getDBProperty("select.all.orders.dbe");
            log.error(errorMsg);
            throw new DBException(errorMsg);
        }
    }

    // only fot payment
    @Override
    public void update(Orders entity) {
        final String query = Prop.getDBProperty("update.orders");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            int k = 1;
            pstmt.setString(k++, entity.getStatus().name());
            pstmt.setLong(k++, entity.getId());
            pstmt.setString(k, Status.NEW.name());

            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();

            String errorMsg = Prop.getDBProperty("update.orders.dbe") + entity.getId();
            log.error(errorMsg);
        }
    }

    @Override
    public void delete(int id) {
    }

    /**
     * This id internal transaction for create order and clean basket after it
     * @param id user id (whose basket is clean up)
     */
    public void deleteAllFromBasket(int id) {
        final String query = Prop.getDBProperty("delete.all.basket");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, id);

            connection.setAutoCommit(false);
            pstmt.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();

            String errorMsg = Prop.getDBProperty("delete.all.basket.dbe") + id;
            log.error(errorMsg);
            try {
                log.warn(Prop.getDBProperty("transaction.rollback"));
                connection.rollback();
            } catch (SQLException e) {
                log.error(Prop.getDBProperty("transaction.rollback.fail"));
            }
        }
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
