package ua.servlet.restaurant.service;

import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.DaoFactory;
import ua.servlet.restaurant.dao.DishesDao;
import ua.servlet.restaurant.dao.entity.Dishes;

import java.util.List;

public class DishesService {
    DaoFactory daoFactory = DaoFactory.getInstance();

    public List<Dishes> getAll() throws DBException {
        try (DishesDao dao = daoFactory.createDishesDao()) {
            return dao.findAll();
        }
    }

}
