package ua.servlet.restaurant.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.DishesDao;
import ua.servlet.restaurant.dao.mapper.DishesMapper;
import ua.servlet.restaurant.dao.entity.Dishes;
import ua.servlet.restaurant.dto.DishesDTO;
import ua.servlet.restaurant.utils.Prop;

import java.sql.*;
import java.util.*;

public class JDBCDishesDao implements DishesDao {
    Logger log = LogManager.getLogger(JDBCDishesDao.class);
    private final Connection connection;
    public JDBCDishesDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Dishes> create(Dishes entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Dishes> findById(int id) throws DBException {
        Optional<Dishes> result = Optional.empty();

        final String query = Prop.getDBProperty("select.dishes");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            DishesMapper dishesMapper = new DishesMapper();
            if (rs.next()) {
                result = Optional.of(dishesMapper.extractFromResultSet(rs));
            }
            rs.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();

            String errorMsg = Prop.getDBProperty("select.dishes.dbe");
            log.error(errorMsg);
            throw new DBException(errorMsg);
        }
    }

    @Override
    public List<Dishes> findAll() throws DBException {
        Map<Long, Dishes> dishes = new HashMap<>();

        final String query = Prop.getDBProperty("select.all.dishes");
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);

            DishesMapper dishesMapper = new DishesMapper();
            while (rs.next()) {
                Dishes dish = dishesMapper.extractFromResultSet(rs);
                dishesMapper.makeUnique(dishes, dish);
            }
            rs.close();
            return new ArrayList<>(dishes.values());
        } catch (SQLException e) {
            e.printStackTrace();

            String errorMsg = Prop.getDBProperty("select.all.dishes.dbe");
            log.error(errorMsg);
            throw new DBException(errorMsg);
        }
    }

    public List<Dishes> findAllPageable(int pageNo, String sort,
                                        String direct, int categoryId) throws DBException {
        Map<Long, Dishes> dishes = new HashMap<>();
        int rowsOnPage = Integer.parseInt(Prop.getProperty("pageable.page"));
        int beginNo = (pageNo - 1) * rowsOnPage + 1;
        int endNo = beginNo + rowsOnPage - 1;

//        final String query = (category.equals("none"))
//                ? Prop.getDBProperty("select.all.dishes.pageable")
//                : Prop.getDBProperty("select.all.dishes.pageable.filter");

//        final String preQuery = (categoryId == 0)
//
//                ? "SELECT * FROM dishes d LEFT JOIN categories c " +
//                "ON d.category_id = c.id ORDER BY ? %s " +
//                "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY"
//
//                : "SELECT * FROM dishes d LEFT JOIN categories c " +
//                "ON d.category_id = c.id WHERE c.id=? ORDER BY ? %s " +
//                "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
//
//        String query = String.format(preQuery, direct.toUpperCase(Locale.ROOT));

        String query = "SELECT * FROM " +
                "(SELECT row_number() OVER (ORDER BY d.id) AS row,* FROM dishes d LEFT JOIN categories c " +
                "ON d.category_id = c.id ORDER BY ? ASC) AS temp " +
                "WHERE temp.row BETWEEN ? AND ?";

        System.out.println(query);

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            int k = 1;
            if (categoryId != 0) {
                pstmt.setInt(k++, categoryId);
            }
//            pstmt.setString(k++, "d." + sort);
            pstmt.setString(k++, "d." + sort);
            pstmt.setInt(k++, beginNo);
            pstmt.setInt(k, endNo);

            ResultSet rs = pstmt.executeQuery();

            DishesMapper dishesMapper = new DishesMapper();
            while (rs.next()) {
                Dishes dish = dishesMapper.extractFromResultSet(rs);
                dishesMapper.makeUnique(dishes, dish);
            }
            rs.close();
            return new ArrayList<>(dishes.values());
        } catch (SQLException e) {
            e.printStackTrace();

            String errorMsg = Prop.getDBProperty("select.all.dishes.dbe");
            log.error(errorMsg);
            throw new DBException(errorMsg);
        }
    }

    @Override
    public void update(Dishes entity) {
//        final String query = Prop.getDBProperty("update.dishes");
        final String query = "UPDATE dishes SET name_en=?, name_ua=?, price=? WHERE name_en=?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            int k = 1;
            pstmt.setString(k++, entity.getNameEn());
            pstmt.setString(k++, entity.getNameUa());
            pstmt.setBigDecimal(k++, entity.getPrice());
            pstmt.setString(k, entity.getNameEn());

            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();

            String errorMsg = Prop.getDBProperty("delete.update.dbe") + entity.getId();
            log.error(errorMsg);
        }
    }

    @Override
    public void delete(int id) {
        final String query = Prop.getDBProperty("delete.dishes");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();

            String errorMsg = Prop.getDBProperty("delete.dishes.dbe") + id;
            log.error(errorMsg);
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
