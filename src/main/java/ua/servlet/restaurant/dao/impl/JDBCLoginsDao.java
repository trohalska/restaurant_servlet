package ua.servlet.restaurant.dao.impl;

import ua.servlet.restaurant.dao.LoginsDao;
import ua.servlet.restaurant.dao.mapper.LoginsMapper;
import ua.servlet.restaurant.model.Logins;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCLoginsDao implements LoginsDao {
    private final Connection connection;
    public JDBCLoginsDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Logins entity) {

    }

    @Override
    public Logins findById(int id) {
        return null;
    }

    @Override
    public List<Logins> findAll() {
        Map<Long, Logins> users = new HashMap<>();
//        Map<Integer, Teacher> teachers = new HashMap<>();

        final String query = "SELECT * FROM login";
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);

            LoginsMapper loginsMapper = new LoginsMapper();
//            StudentMapper studentMapper = new StudentMapper();

            while (rs.next()) {
                Logins login = loginsMapper.extractFromResultSet(rs);
//                Teacher teacher = teacherMapper
//                        .extractFromResultSet(rs);
                login = loginsMapper.makeUnique(users, login);
//                teacher = teacherMapper
//                        .makeUnique(teachers, teacher);
//                student.getTeachers().add(teacher);
            }
            return new ArrayList<>(users.values());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
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
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
//    public Optional<Teacher> findByName(String name) {
//
//        Optional<Teacher> result = Optional.empty();
//        try(PreparedStatement ps = connection.prepareCall("SELECT * FROM teacher WHERE name = ?")){
//            ps.setString( 1, name);
//            ResultSet rs;
//            rs = ps.executeQuery();
//            TeacherMapper mapper = new TeacherMapper();
//            if (rs.next()){
//                result = Optional.of(mapper.extractFromResultSet(rs));
//            }//TODO : ask question how avoid two teachers with the same name
//        }catch (Exception ex){
//            throw new RuntimeException(ex);
//        }
//        return result;
//    }
}
