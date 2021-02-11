package ua.servlet.restaurant.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.LoginsDao;
import ua.servlet.restaurant.dao.mapper.LoginsMapper;
import ua.servlet.restaurant.dao.entity.Logins;
import ua.servlet.restaurant.dao.entity.RoleType;
import ua.servlet.restaurant.utils.Prop;
;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class JDBCLoginsDao implements LoginsDao {
    Logger log = LogManager.getLogger(JDBCLoginsDao.class);
    private final Connection connection;
    public JDBCLoginsDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Logins> create(Logins entity) throws DBException {
        ResultSet rs;

        final String query = Prop.getDBProperty("create.user");
        try (PreparedStatement pstmt =
                     connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            int k = 1;
            pstmt.setString(k++, entity.getLogin());
            pstmt.setString(k++, entity.getEmail());
            pstmt.setString(k++, entity.getPassword());
            pstmt.setString(k++, RoleType.ROLE_CUSTOMER.name());
            pstmt.setTimestamp(k, Timestamp.valueOf(LocalDateTime.now()));

            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    entity.setId(rs.getLong(1));
                }
                rs.close();
            }
            return Optional.of(entity);
        } catch (SQLException ex) {
            ex.printStackTrace();

            String errorMsg = Prop.getDBProperty("create.user.dbe") + entity.getLogin();
            log.error(errorMsg);
            throw new DBException(errorMsg);
        }
    }

    @Override
    public Logins findById(int id) {
        return null;
    }

    @Override
    public List<Logins> findAll() throws DBException {
        Map<Long, Logins> users = new HashMap<>();

        final String query = Prop.getDBProperty("select.all.logins");
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);

            LoginsMapper loginsMapper = new LoginsMapper();
            while (rs.next()) {
                Logins login = loginsMapper.extractFromResultSet(rs);
                login = loginsMapper.makeUnique(users, login);
            }
            return new ArrayList<>(users.values());
        } catch (SQLException e) {
            e.printStackTrace();

            String errorMsg = Prop.getDBProperty("select.all.logins.dbe");
            log.error(errorMsg);
            throw new DBException(errorMsg);
        }
    }

    @Override
    public void update(Logins entity) {

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
            ex.printStackTrace();

            String errorMsg = Prop.getDBProperty("select.login.byLogin.dbe") + login;
            log.error(errorMsg);
            throw new DBException(errorMsg);
        }
    }




    /**
     * transaction example
     */
//    public Optional<Logins> create(Logins entity) {
//        Optional<Logins> result = Optional.empty();
//        ResultSet rs;
//
//        final String query = Prop.getDBProperty("create.user");
//        try (PreparedStatement pstmt =
//                     connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
//
//            int k = 1;
//            pstmt.setString(k++, entity.getLogin());
//            pstmt.setString(k++, entity.getEmail());
//            pstmt.setString(k++, entity.getPassword());
//            pstmt.setString(k++, RoleType.ROLE_CUSTOMER.name());
//            pstmt.setTimestamp(k, Timestamp.valueOf(LocalDateTime.now()));
//
//            connection.setAutoCommit(false);
//            if (pstmt.executeUpdate() > 0) {
//                rs = pstmt.getGeneratedKeys();
//                if (rs.next()) {
//                    entity.setId(rs.getLong(1));
//                }
//                rs.close();
//            }
//            connection.commit();
//            result = Optional.of(entity);
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            System.err.println(Prop.getDBProperty("create.user.dbe") + entity.getLogin());
//            try {
//                System.err.println("Transaction is being rolled back");
//                connection.rollback();
//            } catch (SQLException e) {
//                System.err.println("Error in transaction rollback");
//            }
//        }
//        return result;
//    }

    /**
     * Insert team into table teams.
     * @param team income
     */
//    public void insertTeam(Team team) {
//        ResultSet rs = null;
//
//        try(Connection con = getConnection(connectionUrl);
//            PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_TEAM, Statement.RETURN_GENERATED_KEYS)) {
//
//            pstmt.setString(1, team.getName());
//
//            if (pstmt.executeUpdate() > 0) {
//                rs = pstmt.getGeneratedKeys();
//                if (rs.next()) {
//                    team.setId(rs.getLong(1));
//                }
//            }
//        } catch (SQLException ex) {
//            printError(DBE_INSERT_TEAM + team.getName());
//        } finally {
//            close(rs);
//        }
//    }

//    /**
//     * Get user from table users by login.
//     * @param login income
//     * @return object User
//     */
//    public Logins getUser(String login) throws SQLException {
//        Logins user = new Logins();
//        ResultSet rs = null;
//
//        try(Connection con = getConnection(connectionUrl);
//            PreparedStatement pstmt = con.prepareStatement(SQL_GET_USER)) {
//
//            pstmt.setString(1, login);
//
//            rs = pstmt.executeQuery();
//            if (rs.next()) {
//                user.setId(rs.getLong(1));
//                user.setLogin(login);
//            }
//        } catch (SQLException ex) {
//            printError(DBE_GET_USER + login);
//        } finally {
//            rs.close();
//        }
//        return user;
//    }

    /**
     * Get team from table teams by team name.
     * @param name income
     * @return object Team
     */
//    public Team getTeam(String name) {
//        Team team = new Team();
//        ResultSet rs = null;
//
//        try(Connection con = getConnection(connectionUrl);
//            PreparedStatement pstmt = con.prepareStatement(SQL_GET_TEAM)) {
//
//            pstmt.setString(1, name);
//
//            rs = pstmt.executeQuery();
//            if (rs.next()) {
//                team.setId(rs.getLong(1));
//                team.setName(name);
//            }
//        } catch (SQLException ex) {
//            printError(DBE_GET_TEAM + team.getName());
//        } finally {
//            close(rs);
//        }
//        return team;
//    }

    /**
     * Get all teams in which user participate (by user id)
     * @param user income
     * @return list of teams(objects)
     */
//    public List<Team> getUserTeams(User user) {
//        List<Team> teams = new ArrayList<>();
//        ResultSet rs = null;
//
//        try(Connection con = getConnection(connectionUrl);
//            PreparedStatement pstmt = con.prepareStatement(SQL_GET_TEAMS_BY_USER)) {
//
//            pstmt.setLong(1, user.getId());
//
//            rs = pstmt.executeQuery();
//            while (rs.next()) {
//                teams.add(mapTeam(rs));
//            }
//        } catch (SQLException ex) {
//            printError(DBE_GET_TEAMS_BY_USER + user.getLogin());
//        } finally {
//            close(rs);
//        }
//        return teams;
//    }

    /**
     * Insert values in table users_teams
     * that represents participation one user in many teams.
     * Will roll back all transaction if smth goes wrong.
     * @param user income user
     * @param teams array of teams
     * @throws SQLException rollback exception
     */
//    public void setTeamsForUser(User user, Team... teams) throws SQLException {
//        Connection con = null;
//
//        try {
//            con = getConnection(connectionUrl);
//            con.setAutoCommit(false);
//            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
//
//            for (Team t : teams) {
//                insertUserTeam(con, user.getId(), t.getId());
//            }
//            con.commit();
//        } catch (SQLException ex) {
//            assert con != null;
//            con.rollback();
//            printError(DBE_SET_TEAMS_FOR_USER + user.getLogin());
//        } finally {
//            close(con);
//        }
//    }

    /**
     * Additional method for setTeamsForUser. Set one row in users_teams table.
     * @param con opened db connection
     * @param uid user id
     * @param tid team id
     */
//    public void insertUserTeam(Connection con, long uid, long tid) {
//        PreparedStatement pstmt = null;
//
//        try {
//            pstmt = con.prepareStatement(SQL_SET_TEAMS_FOR_USER, Statement.RETURN_GENERATED_KEYS);
//
//            int k = 1;
//            pstmt.setLong(k++, uid);
//            pstmt.setLong(k, tid);
//
//            pstmt.executeUpdate();
//        } catch (SQLException ex) {
//            printError("Cannot insert users teams for user with id=" + uid);
//        } finally {
//            close(pstmt);
//        }
//    }

    /**
     * Find all users in table users.
     * @return list of users
     */
//    public List<User> findAllUsers() {
//        List<User> users = new ArrayList<>();
//
//        try (Connection con = getConnection(connectionUrl);
//             Statement stmt = con.createStatement();
//             ResultSet rs = stmt.executeQuery(SQL_FIND_ALL_USERS)) {
//
//            while (rs.next()) {
//                users.add(mapUser(rs));
//            }
//        } catch (SQLException ex) {
//            printError(DBE_FIND_ALL_USERS);
//        }
//        return users;
//    }

    /**
     * Find all teams in table teams.
     * @return list of teams
     */
//    public List<Team> findAllTeams() {
//        List<Team> teams = new ArrayList<>();
//
//        try (Connection con = getConnection(connectionUrl);
//             Statement stmt = con.createStatement();
//             ResultSet rs = stmt.executeQuery(SQL_FIND_ALL_TEAMS)) {
//
//            while (rs.next()) {
//                teams.add(mapTeam(rs));
//            }
//        } catch (SQLException ex) {
//            printError(DBE_FIND_ALL_TEAMS);
//        }
//        return teams;
//    }

    /**
     * Update team login in table teams (by id).
     * If id will be changed - unpredictable behavior.
     * @param team income
     */
//    public void updateTeam(Team team) {
//        Connection con = null;
//        PreparedStatement pstmt = null;
//
//        try {
//            con = getConnection(connectionUrl);
//            pstmt = con.prepareStatement(SQL_UPDATE_TEAM);
//
//            int k = 1;
//            pstmt.setString(k++, team.getName());
//            pstmt.setLong(k, team.getId());
//
//            pstmt.executeUpdate();
//        } catch (SQLException ex) {
//            printError(DBE_UPDATE_TEAM + team.getId());
//        } finally {
//            close(pstmt);
//            close(con);
//        }
//
//    }

    /**
     * Delete team from table teams and all records from table users_teams (DELETE CASCADE)
     * @param team income
     */
//    public void deleteTeam(Team team) {
//        Connection con = null;
//        PreparedStatement pstmt = null;
//
//        try {
//            con = getConnection(connectionUrl);
//            pstmt = con.prepareStatement(SQL_DELETE_TEAM);
//            pstmt.setLong(1, team.getId());
//
//            pstmt.executeUpdate();
//        } catch (SQLException ex) {
//            printError(DBE_DELETE_TEAM + team.getId());
//        } finally {
//            close(pstmt);
//            close(con);
//        }
//
//    }

}
