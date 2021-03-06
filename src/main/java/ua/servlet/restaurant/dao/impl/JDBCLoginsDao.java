package ua.servlet.restaurant.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.LoginsDao;
import ua.servlet.restaurant.dao.mapper.LoginsMapper;
import ua.servlet.restaurant.dao.entity.Logins;
import ua.servlet.restaurant.dao.entity.RoleType;
import ua.servlet.restaurant.utils.Prop;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class JDBCLoginsDao implements LoginsDao {
    // todo loggers - private static final everywhere
    private static final Logger log = LogManager.getLogger(JDBCLoginsDao.class);
    private final Connection connection;
    // todo make factory for returning connection (NOT create classes with all functions)
    public JDBCLoginsDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * For registration new user (customer)
     * @param entity new user
     * @return login
     * @throws DBException if cannot create
     */
    // todo return login (NOT return optional if there are no nulls)
    @Override
    public Optional<Logins> create(Logins entity) throws DBException {
        ResultSet rs;

        final String query = Prop.getDBProperty("create.user");
        try (PreparedStatement pstmt =
                     connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            // todo do not use k++ anywhere
            // todo close to finally or try with resources
            pstmt.setString(1, entity.getLogin());
            pstmt.setString(2, entity.getEmail());
            pstmt.setString(3, entity.getPassword());
            pstmt.setString(4, RoleType.ROLE_CUSTOMER.name());
            pstmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));

            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    entity.setId(rs.getLong(1));
                }
                rs.close();
            }
            return Optional.of(entity);
        } catch (SQLException ex) {
            String errorMsg = Prop.getDBProperty("create.user.dbe") + entity.getLogin();
            log.error(errorMsg);
            throw new DBException(errorMsg);
        }
    }

    /**
     * For login page
     * @param login login
     * @return login if found
     * @throws DBException if cannot find
     */
    public Optional<Logins> findByLogin(String login) throws DBException {
        Optional<Logins> result = Optional.empty();

        try (PreparedStatement pstmt =
                     connection.prepareStatement(Prop.getDBProperty("select.login.byLogin"))) {
            pstmt.setString(1, login);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                result = Optional.of(new LoginsMapper().extractFromResultSet(rs));
            }
            rs.close();
            return result;
        } catch (SQLException ex) {
            String errorMsg = Prop.getDBProperty("select.login.byLogin.dbe") + login;
            log.error(errorMsg);
            throw new DBException(errorMsg);
        }
    }

    @Deprecated
    @Override
    public Optional<Logins> findById(int id) {
        return Optional.empty();
    }

    @Deprecated
    @Override
    public Optional<List<Logins>> findAll() throws DBException {
        Map<Long, Logins> users = new HashMap<>();

        // todo close to finally or make try with resources
        final String query = Prop.getDBProperty("select.all.logins");
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            LoginsMapper loginsMapper = new LoginsMapper();
            while (rs.next()) {
                Logins login = loginsMapper.extractFromResultSet(rs);
                loginsMapper.makeUnique(users, login);
            }
            List<Logins> list = new ArrayList<>(users.values());
            return list.isEmpty() ? Optional.empty() : Optional.of(list);
        } catch (SQLException e) {
            e.printStackTrace();

            String errorMsg = Prop.getDBProperty("select.all.logins.dbe");
            log.error(errorMsg);
            throw new DBException(errorMsg);
        }
    }

    @Deprecated
    @Override
    public void update(Logins entity) {

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
