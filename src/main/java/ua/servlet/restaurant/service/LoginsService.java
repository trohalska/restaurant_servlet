package ua.servlet.restaurant.service;

import org.mindrot.jbcrypt.BCrypt;
import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.DaoFactory;
import ua.servlet.restaurant.dao.LoginsDao;
import ua.servlet.restaurant.dao.entity.Logins;
import ua.servlet.restaurant.utils.Prop;

import java.util.List;

public class LoginsService {
    DaoFactory daoFactory = DaoFactory.getInstance();

    public Logins create(Logins user) throws DBException {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));

        try (LoginsDao dao = daoFactory.createLoginsDao()) {
            return dao.create(user).orElseThrow(
                    () -> new DBException(
                            Prop.getDBProperty("create.user.dbe") + user.getLogin())
            );
        }
    }

    public Logins findByLogin(String login) throws DBException {
        try (LoginsDao dao = daoFactory.createLoginsDao()) {
            return dao.findByLogin(login).orElseThrow(
                    () -> new DBException(
                            Prop.getDBProperty("select.login.byLogin.dbe") + login)
            );
        }
    }

    public List<Logins> getAll() throws DBException {
        try (LoginsDao dao = daoFactory.createLoginsDao()) {
            return dao.findAll();
        }
    }


//    public Optional<Teacher> login(String name){
//        Optional<Teacher> result; //= Optional.empty();
//        try(TeacherDao teacherDao = daoFactory.createTeacherDao()){
//            result = teacherDao.findByName(name);
//        }
//        return result;
//    }


}
