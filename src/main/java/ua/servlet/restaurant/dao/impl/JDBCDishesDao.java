package ua.servlet.restaurant.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.DishesDao;
import ua.servlet.restaurant.dao.entity.Categories;
import ua.servlet.restaurant.dao.mapper.CategoriesMapper;
import ua.servlet.restaurant.dao.mapper.DishesMapper;
import ua.servlet.restaurant.dao.entity.Dishes;
import ua.servlet.restaurant.dto.Page;
import ua.servlet.restaurant.dto.converter.CategoriesDTOConverter;
import ua.servlet.restaurant.dto.converter.DishesDTOConverter;
import ua.servlet.restaurant.utils.Prop;

import java.sql.*;
import java.util.*;

public class JDBCDishesDao implements DishesDao {
    Logger log = LogManager.getLogger(JDBCDishesDao.class);
    private final Connection connection;
    public JDBCDishesDao(Connection connection) {
        this.connection = connection;
    }

    @Deprecated
    @Override
    public Optional<Dishes> create(Dishes entity) {
        return Optional.empty();
    }

    // todo for update
    /**
     * Find dish for update
     * @param id dish id
     * @return dish
     * @throws DBException if cannot find
     */
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
            String errorMsg = Prop.getDBProperty("select.dishes.dbe");
            log.error(errorMsg);
            throw new DBException(errorMsg);
        }
    }

    /**
     * Find all dishes for menu page (old version)
     * @return list of dishes
     * @throws DBException if cannot find
     */
    @Deprecated
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
            String errorMsg = Prop.getDBProperty("select.all.dishes.dbe");
            log.error(errorMsg);
            throw new DBException(errorMsg);
        }
    }

    /**
     * TRANSACTION
     * Find all dishes and all categories for menu page
     * @param pageNo current page
     * @param sort sort field
     * @param direct direction ASC or DESC
     * @param categoryId filter by category
     * @param locale locale for dto
     * @return Page class
     * @throws DBException if cannot find
     */
    public Page findAllPageable(int pageNo, String sort,
                                String direct, int categoryId, String locale) throws DBException {
        int rowsOnPage = Integer.parseInt(Prop.getProperty("pageable.page"));
        int beginNo = (pageNo - 1) * rowsOnPage + 1;
        int endNo = beginNo + rowsOnPage - 1;

        Long totalPages;
        List<Dishes> dishes = new ArrayList<>();
        Map<Long, Categories> categories = new HashMap<>();

        final String queryCategories = Prop.getDBProperty("select.all.categories");
        final String preQuery = (categoryId == 0)
                ? Prop.getDBProperty("select.all.dishes.pageable")
                : Prop.getDBProperty("select.all.dishes.pageable.filter");

        String query = String.format(preQuery, "d." + sort, direct.toUpperCase(Locale.ROOT));
        if (!sort.equals("id")) {
            query += " ORDER BY " + sort + " " + direct.toUpperCase(Locale.ROOT);
        }

        try (PreparedStatement pstmt = connection.prepareStatement(query);
            Statement st = connection.createStatement()) {
            int k = 1;
            if (categoryId != 0) {
                pstmt.setInt(k++, categoryId);
                pstmt.setInt(k++, categoryId);
            }
            pstmt.setInt(k++, beginNo);
            pstmt.setInt(k, endNo);

            connection.setAutoCommit(false);
            ResultSet rs = pstmt.executeQuery();
            ResultSet rs2 = st.executeQuery(queryCategories);
            connection.commit();

            Long totalRows = resultSetMapper(rs, dishes);
            resultSetMapper(rs2, categories);
            totalPages = getTotal(totalRows, rowsOnPage);
            rs.close();
            rs2.close();

            return Page.builder()
                    .dishes(DishesDTOConverter.convertList(dishes, locale))
                    .categories(CategoriesDTOConverter.convertList(
                            new ArrayList<>(categories.values()), locale))
                    .totalPages(totalPages)
                    .build();

        } catch (SQLException e) {
            String errorMsg = Prop.getDBProperty("select.all.dishes.dbe");
            log.error(errorMsg);
            try {
                log.warn(Prop.getDBProperty("transaction.rollback"));
                connection.rollback();
            } catch (SQLException ex) {
                log.error(Prop.getDBProperty("transaction.rollback.fail"));
            }
            throw new DBException(errorMsg);
        }
    }

    /**
     * Internal mapper for all categories
     * @param rs categories result set
     * @param categories categories list
     * @return true if all is ok
     * @throws SQLException if cannot get from rs
     */
    private boolean resultSetMapper(ResultSet rs, Map<Long, Categories> categories) throws SQLException {
        CategoriesMapper categoriesMapper = new CategoriesMapper();
        while (rs.next()) {
            Categories category = categoriesMapper.extractFromResultSet(rs);
            categoriesMapper.makeUnique(categories, category);
        }
        return true;
    }

    /**
     * Internal mapper for all dishes and count dishes
     * @param rs dishes result set
     * @param dishes dish list
//     * @param totalRows to count dishes
     * @return totalRows
     * @throws SQLException if cannot get from rs
     */
    private Long resultSetMapper(ResultSet rs, List<Dishes> dishes) throws SQLException {
        DishesMapper dishesMapper = new DishesMapper();
        long totalRows = -1;
        while (rs.next()) {
            dishes.add(dishesMapper.extractFromResultSet(rs));
            if (totalRows == -1) {
                totalRows = rs.getLong("count");
            }
        }
        return totalRows;
    }

    /**
     * Internal util for count total pages
     * @param totalRows amount of dishes
     * @param rowsOnPage amount rows by page
     * @return total pages
     */
    private Long getTotal(Long totalRows, int rowsOnPage) {
        if (totalRows != null) {
            totalRows = (totalRows % rowsOnPage == 0)
                    ? totalRows/rowsOnPage
                    : totalRows/rowsOnPage + 1;
        }
        return totalRows;
    }

    // todo for update delete dish
    @Deprecated
    @Override
    public void update(Dishes entity) {
        final String query = Prop.getDBProperty("update.dishes");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            int k = 1;
            pstmt.setString(k++, entity.getNameEn());
            pstmt.setString(k++, entity.getNameUa());
            pstmt.setBigDecimal(k++, entity.getPrice());
            pstmt.setString(k, entity.getNameEn());

            pstmt.executeUpdate();
        } catch (SQLException ex) {
            String errorMsg = Prop.getDBProperty("delete.update.dbe") + entity.getId();
            log.error(errorMsg);
        }
    }

    @Deprecated
    @Override
    public void delete(int id) {
        final String query = Prop.getDBProperty("delete.dishes");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
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
