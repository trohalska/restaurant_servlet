package ua.servlet.restaurant.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.OrdersDao;
import ua.servlet.restaurant.dao.entity.Orders;
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

    /**
     * TRANSACTION
     * Create order - get all from basket by user id, get sum prices, delete all from basket
     * @param entity Orders
     * @return created order
     * @throws DBException if cannot create order
     */
    @Override
    public Optional<Orders> create(Orders entity) throws DBException {
        ResultSet rs;

        final String query = Prop.getDBProperty("create.order");
        final String queryDelete = Prop.getDBProperty("delete.all.basket");
        try (PreparedStatement pstmt =
                     connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement pstmtDelete = connection.prepareStatement(queryDelete)) {

            int k = 1;
            pstmt.setString(k++, entity.getStatus().name());
            pstmt.setLong(k++, entity.getLogin().getId());
            pstmt.setLong(k, entity.getLogin().getId());

            pstmtDelete.setLong(1, entity.getLogin().getId());

            connection.setAutoCommit(false);
            int resultCreate = pstmt.executeUpdate();
            pstmtDelete.executeUpdate();
            connection.commit();

            if (resultCreate > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    entity.setId(rs.getLong("id"));
                    entity.setTime(rs.getTimestamp("time").toLocalDateTime());
                    entity.setTotalPrice(rs.getBigDecimal("total_price"));
                }
                rs.close();
            }
        } catch (SQLException ex) {
            String errorMsg = Prop.getDBProperty("create.order.dbe");
            log.error(errorMsg);
            try {
                log.warn(Prop.getDBProperty("transaction.rollback"));
                connection.rollback();
            } catch (SQLException e) {
                log.error(Prop.getDBProperty("transaction.rollback.fail"));
            }
        }
        return Optional.of(entity);
    }

    /**
     * Find order by id for payment page
     * @param id order id
     * @return order
     * @throws DBException if id invalid
     */
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
            String errorMsg = Prop.getDBProperty("select.orders.dbe");
            log.error(errorMsg);
            throw new DBException(errorMsg);
        }
    }

    /**
     * Select all orders for manager page
     * @return list of orders
     * @throws DBException if cannot find
     */
    @Override
    public Optional<List<Orders>> findAll() throws DBException {
        final String query = Prop.getDBProperty("select.all.orders.manager");
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);

            OrdersMapper ordersMapper = new OrdersMapper();
            List<Orders> result = new ArrayList<>();
            while (rs.next()) {
                result.add(ordersMapper.extractFromResultSet(rs));
            }
            rs.close();
            return result.isEmpty() ? Optional.empty() : Optional.of(result);
        } catch (SQLException e) {
            String errorMsg = Prop.getDBProperty("select.all.orders.manager.dbe");
            log.error(errorMsg);
            throw new DBException(errorMsg);
        }
    }

    /**
     * Select only users orders for user page by user id
     * @param id user id
     * @return optional list of orders
     * @throws DBException if user id invalid
     */
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
            String errorMsg = Prop.getDBProperty("select.all.orders.dbe");
            log.error(errorMsg);
            throw new DBException(errorMsg);
        }
    }

    //

    /**
     * Update for payment and change status by manager
     * @param entity Orders (for order id and status)
     */
    @Override
    public void update(Orders entity) {
        final String query = Prop.getDBProperty("update.orders");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            int k = 1;
            pstmt.setString(k++, entity.getStatus().next().name());
            pstmt.setLong(k++, entity.getId());
            pstmt.setString(k, entity.getStatus().name());

            pstmt.executeUpdate();
        } catch (SQLException ex) {
            String errorMsg = Prop.getDBProperty("update.orders.dbe") + entity.getId();
            log.error(errorMsg);
        }
    }

    @Deprecated
    @Override
    public void delete(Long login_id, Long id) { }

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
