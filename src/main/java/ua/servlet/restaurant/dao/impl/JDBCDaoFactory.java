package ua.servlet.restaurant.dao.impl;

import ua.servlet.restaurant.dao.DaoFactory;
import ua.servlet.restaurant.dao.LoginsDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {
    private final DataSource dataSource = ConnectionPoolHolder.getDataSource();

    @Override
    public LoginsDao createLoginsDao() {
        return new JDBCLoginsDao(getConnection());
    }

//    @Override
//    public TeacherDao createTeacherDao() {
//        return new JDBCTeacherDao(getConnection());
//    }
//    @Override
//    public StudentDao createStudentDao() {
//        return new JDBCStudentDao(getConnection());
//    }

    private Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
