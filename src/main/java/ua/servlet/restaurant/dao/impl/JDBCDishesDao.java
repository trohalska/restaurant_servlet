package ua.servlet.restaurant.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.DishesDao;
import ua.servlet.restaurant.dao.mapper.DishesMapper;
import ua.servlet.restaurant.dao.entity.Dishes;
import ua.servlet.restaurant.dto.DishesDTO;
import ua.servlet.restaurant.dto.Page;
import ua.servlet.restaurant.dto.converter.DishesDTOConverter;
import ua.servlet.restaurant.utils.Prop;
import ua.servlet.restaurant.utils.Utils;

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

    public Page findAllPageable(int pageNo, String sort,
                                String direct, int categoryId, String locale) throws DBException {
        int rowsOnPage = Integer.parseInt(Prop.getProperty("pageable.page"));
        int beginNo = (pageNo - 1) * rowsOnPage + 1;
        int endNo = beginNo + rowsOnPage - 1;

//        final String query = (category.equals("none"))
//                ? Prop.getDBProperty("select.all.dishes.pageable")
//                : Prop.getDBProperty("select.all.dishes.pageable.filter");

        final String preQuery = (categoryId == 0)

                ? "SELECT (select count(*) from dishes),* FROM " +
                "(SELECT row_number() OVER (ORDER BY %1$s %2$s) AS row,* FROM dishes d LEFT JOIN categories c " +
                "ON d.category_id = c.id ORDER BY %1$s %2$s) AS temp " +
                "WHERE temp.row BETWEEN ? AND ?"

                : "SELECT (select count(*) from dishes WHERE category_id=?),* FROM " +
                "(SELECT row_number() OVER (ORDER BY %1$s %2$s) AS row,* FROM dishes d LEFT JOIN categories c " +
                "ON d.category_id = c.id WHERE c.id=? ORDER BY %1$s %2$s) AS temp " +
                "WHERE temp.row BETWEEN ? AND ?";

        String query = String.format(preQuery, "d." + sort, direct.toUpperCase(Locale.ROOT));
        if (!sort.equals("id")) {
            query += " ORDER BY " + sort + " " + direct.toUpperCase(Locale.ROOT);
        }

        System.out.println(query);

        try (PreparedStatement pstmt = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            int k = 1;
            if (categoryId != 0) {
                pstmt.setInt(k++, categoryId);
                pstmt.setInt(k++, categoryId);
            }
            pstmt.setInt(k++, beginNo);
            pstmt.setInt(k, endNo);

            ResultSet rs = pstmt.executeQuery();

            Long totalRows = null;
            List<Dishes> dishes = new ArrayList<>();
            DishesMapper dishesMapper = new DishesMapper();
            while (rs.next()) {
                dishes.add(dishesMapper.extractFromResultSet(rs));
                if (totalRows == null) {
                    totalRows = rs.getLong("count");
                }
            }
            rs.close();

            if (totalRows != null) {
                totalRows = (totalRows % rowsOnPage == 0)
                        ? totalRows/rowsOnPage
                        : totalRows/rowsOnPage + 1;
            }
            return Page.builder()
                    .dishes(DishesDTOConverter.convertList(dishes, locale))
                    .totalPages(totalRows)
                    .build();

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
