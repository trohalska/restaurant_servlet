package ua.servlet.restaurant.repository;

import ua.servlet.restaurant.utils.SQLConst;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class DBManager {
    private static DBManager dbManager;
    public static String connectionUrl;

    private DBManager() {}

    public static synchronized DBManager getInstance() {
        connectionUrl = getProperties();
        if (dbManager == null) {
            dbManager = new DBManager();
        }
        return dbManager;
    }

    public Connection getConnection(String connectionUrl) throws SQLException {
        return DriverManager.getConnection(connectionUrl);
    }

    /**
     * Incoming database url value from property file.
     * @return connection url as String
     */
    public static String getProperties() {
        try (FileInputStream fis = new FileInputStream(SQLConst.PROP_FILE)) {
            Properties p = new Properties();
            p.load(fis);
            return (String)p.get(SQLConst.CONN_PROP);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Prints errors to STDERR (replace with logger)
     * @param err income
     */
    private static void printError(String err) {
        System.err.println(err);
    }

    /**
     * Insert user into table users.
     * @param user income
     */
//    public void insertUser(User user) {
//        ResultSet rs = null;
//
//        try(Connection con = getConnection(connectionUrl);
//            PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
//
//            pstmt.setString(1, user.getLogin());
//
//            if (pstmt.executeUpdate() > 0) {
//                rs = pstmt.getGeneratedKeys();
//                if (rs.next()) {
//                    user.setId(rs.getLong(1));
//                }
//            }
//        } catch (SQLException ex) {
//            printError(DBE_INSERT_USER + user.getLogin());
//        } finally {
//            close(rs);
//        }
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

    /**
     * Create object User from ResultSet
     * @param rs income
     * @return object User
     * @throws SQLException sql exception
     */
//    private User mapUser(ResultSet rs) throws SQLException {
//        User user = new User();
//        user.setId(rs.getLong(ID));
//        user.setLogin(rs.getString(USER_LOGIN));
//        return user;
//    }

    /**
     * Create object Team from ResultSet
     * @param rs income
     * @return object Team
     * @throws SQLException sql exception
     */
//    private Team mapTeam(ResultSet rs) throws SQLException {
//        Team team = new Team();
//        team.setId(rs.getLong(ID));
//        team.setName(rs.getString(TEAM_NAME));
//        return team;
//    }

    /**
     * Close connection to database
     * @param ac AutoCloseable
     */
//    private void close(AutoCloseable ac) {
//        if (ac != null) {
//            try {
//                ac.close();
//            } catch (Exception ex) {
//                throw new IllegalStateException("Cannot close " + ac);
//            }
//        }
//    }
}
