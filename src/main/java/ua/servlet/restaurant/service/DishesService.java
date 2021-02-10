package ua.servlet.restaurant.service;

import ua.servlet.restaurant.dao.DaoFactory;
import ua.servlet.restaurant.dao.DishesDao;
import ua.servlet.restaurant.dao.entity.Dishes;

import java.util.List;

public class DishesService {
    DaoFactory daoFactory = DaoFactory.getInstance();

    public List<Dishes> getAll(){
        try (DishesDao dao = daoFactory.createDishesDao()) {
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
