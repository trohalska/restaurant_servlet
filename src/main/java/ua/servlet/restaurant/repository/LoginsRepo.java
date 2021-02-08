package ua.servlet.restaurant.repository;

import ua.servlet.restaurant.model.Logins;
import ua.servlet.restaurant.model.RoleType;
import ua.servlet.restaurant.utils.SQLConst;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.sql.DriverManager.*;

public class LoginsRepo {
    String connectionUrl;
    public LoginsRepo() {
        connectionUrl = Objects.requireNonNull(DBManager.getProperties());
    }

    public Optional<Logins> insert(Logins user, RoleType role) {
        ResultSet rs;

        try(Connection con = getConnection(connectionUrl);
            PreparedStatement pstmt = con.prepareStatement(
                    "INSERT INTO login (login, email, password, role, time) VALUES (?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS)) {

            int k = 1;
            pstmt.setString(k++, user.getLogin());
            pstmt.setString(k++, user.getEmail());
            pstmt.setString(k++, user.getPassword());
            pstmt.setString(k++, role.name());
            pstmt.setTimestamp(k, Timestamp.valueOf(LocalDateTime.now()));

            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    user.setId(rs.getLong(1));
                }
                rs.close();
            }
        } catch (SQLException ex) {
            System.err.println(SQLConst.DBE_INSERT_USER + user.getLogin());
            ex.printStackTrace();
        }
        return Optional.of(user);
    }

    public Optional<Logins> get(String login) {
        Logins user = null;
        ResultSet rs;

        try(Connection con = getConnection(connectionUrl);
            PreparedStatement pstmt = con.prepareStatement(SQLConst.SQL_GET_USER)) {
            pstmt.setString(1, login);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = Logins.builder()
                        .id(rs.getLong(1))
                        .login(login)
                        .build();
            }
            rs.close();
        } catch (SQLException ex) {
            System.err.println(SQLConst.DBE_GET_USER + login);
            ex.printStackTrace();
        }
        return (user == null) ? Optional.empty() : Optional.of(user);
    }

    public List<Logins> getAll() {
        List<Logins> list = new ArrayList<>();
        ResultSet rs;

        try(Connection con = getConnection(connectionUrl);
            PreparedStatement pstmt = con.prepareStatement(SQLConst.SQL_GET_All_USERS)) {

            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(Logins.builder()
                        .id(rs.getLong(1))
                        .login(rs.getString(3))
                        .email(rs.getString(2))
                        .build());
            }
            rs.close();
        } catch (SQLException ex) {
            System.err.println(SQLConst.DBE_GET_USER);
            ex.printStackTrace();
        }
        return list;
    }
}
