package ua.servlet.restaurant.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.servlet.restaurant.dao.CategoriesDao;
import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.entity.Categories;
import ua.servlet.restaurant.dao.entity.Dishes;
import ua.servlet.restaurant.dao.mapper.CategoriesMapper;
import ua.servlet.restaurant.dao.mapper.DishesMapper;
import ua.servlet.restaurant.utils.Prop;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class JDBCCategoriesDao implements CategoriesDao {
    Logger log = LogManager.getLogger(JDBCCategoriesDao.class);
    private final Connection connection;
    public JDBCCategoriesDao(Connection connection) {
        this.connection = connection;
    }

    @Deprecated
    @Override
    public Optional<Categories> create(Categories entity) throws DBException {
        return Optional.empty();
    }

    @Deprecated
    @Override
    public Optional<Categories> findById(int id) throws DBException {
        return Optional.empty();
    }

    /**
     * Get all categories for create new dish page
     * @return list of categories
     * @throws DBException if cannot find
     */
    @Override
    public Optional<List<Categories>> findAll() throws DBException {
        Map<Long, Categories> categories = new HashMap<>();

        final String query = Prop.getDBProperty("select.all.categories");
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);

            CategoriesMapper categoriesMapper = new CategoriesMapper();
            while (rs.next()) {
                Categories c = categoriesMapper.extractFromResultSet(rs);
                categoriesMapper.makeUnique(categories, c);
            }
            rs.close();
            List<Categories> list = new ArrayList<>(categories.values());
            return list.isEmpty() ? Optional.empty() : Optional.of(list);
        } catch (SQLException e) {
            String errorMsg = Prop.getDBProperty("select.all.categories.dbe");
            log.error(errorMsg);
            throw new DBException(errorMsg);
        }
    }

    @Deprecated
    @Override
    public void update(Categories entity) { }

    @Deprecated
    @Override
    public void delete(Long login_id, Long id) throws DBException { }

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
