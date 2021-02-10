package ua.servlet.restaurant.service;

import org.mindrot.jbcrypt.BCrypt;
import ua.servlet.restaurant.dao.DaoFactory;
import ua.servlet.restaurant.dao.LoginsDao;
import ua.servlet.restaurant.model.Logins;
import ua.servlet.restaurant.utils.Prop;

import java.util.List;
import java.util.NoSuchElementException;

public class LoginsService {
    DaoFactory daoFactory = DaoFactory.getInstance();

    public Logins create(Logins user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));

        try (LoginsDao dao = daoFactory.createLoginsDao()) {
            return dao.create(user).orElseThrow(
                    () -> new NoSuchElementException(
                            Prop.getDBProperty("create.user.dbe") + user.getLogin())
            );
        }
    }

    public Logins findByLogin(String login) {
        try (LoginsDao dao = daoFactory.createLoginsDao()) {
            return dao.findByLogin(login).orElseThrow(
                    () -> new NoSuchElementException(
                            Prop.getDBProperty("select.login.byLogin.dbe") + login)
            );
        }
    }

    public List<Logins> getAll(){
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
