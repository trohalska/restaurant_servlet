package ua.servlet.restaurant.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.servlet.restaurant.dao.BasketsDao;
import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.entity.Baskets;
import ua.servlet.restaurant.dao.mapper.BasketsMapper;
import ua.servlet.restaurant.utils.Prop;

import java.sql.*;
import java.util.*;

public class JDBCBasketsDao implements BasketsDao {
    Logger log = LogManager.getLogger(JDBCBasketsDao.class);
    private final Connection connection;
    public JDBCBasketsDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Baskets> create(Baskets entity) throws DBException {
        ResultSet rs;

        final String query = Prop.getDBProperty("create.basket");
        try (PreparedStatement pstmt =
                     connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            int k = 1;
            pstmt.setLong(k++, entity.getDish().getId());
            pstmt.setLong(k, entity.getLogin().getId());

            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    entity.setId(rs.getLong(1));
                }
                rs.close();
            }
            return Optional.of(entity);
        } catch (SQLException ex) {
            String errorMsg = Prop.getDBProperty("create.basket.dbe") + entity.getDish().getId();
            log.error(errorMsg);
            throw new DBException(errorMsg);
        }
    }

    @Deprecated
    @Override
    public Optional<Baskets> findById(int id) {
        return Optional.empty();
    }

    @Deprecated
    @Override
    public List<Baskets> findAll() {
        return null;
    }

    /**
     * Get all basket's items by user id
     * @param id user id
     * @return list of basket's items
     * @throws DBException if cannot find
     */
    public Optional<List<Baskets>> findAllByLoginId(Long id) throws DBException {
        List<Baskets> baskets = new ArrayList<>();

        final String query = Prop.getDBProperty("select.all.baskets");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            BasketsMapper basketsMapper = new BasketsMapper();
            while (rs.next()) {
                baskets.add(basketsMapper.extractFromResultSet(rs));
            }
            rs.close();
            return baskets.isEmpty() ? Optional.empty() : Optional.of(baskets);
        } catch (SQLException e) {
            String errorMsg = Prop.getDBProperty("select.all.baskets.dbe");
            log.error(errorMsg);
            throw new DBException(errorMsg);
        }
    }

    @Deprecated
    @Override
    public void update(Baskets entity) { }

    /**
     * Delete item from basket
     * @param id id deleted item
     */
    @Override
    public void delete(int id) {
        final String query = Prop.getDBProperty("delete.basket");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            String errorMsg = Prop.getDBProperty("delete.baskets.dbe") + id;
            log.error(errorMsg);
        }
    }

    /**
     * TRANSACTION
     * Delete all items from basket by user id
     * @param id user id
     */
    public void deleteAllByLoginId(int id) {
        final String query = Prop.getDBProperty("delete.all.basket");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, id);

            connection.setAutoCommit(false);
            pstmt.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
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
