package ua.servlet.restaurant.service;

import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.DaoFactory;
import ua.servlet.restaurant.dao.DishesDao;
import ua.servlet.restaurant.dao.entity.Dishes;
import ua.servlet.restaurant.dto.DishesDTO;
import ua.servlet.restaurant.dto.converter.DishesDTOConverter;
import ua.servlet.restaurant.utils.Prop;

import java.util.List;

public class DishesService {
    DaoFactory daoFactory = DaoFactory.getInstance();

    // TODO create

    public Dishes findById(Long id) throws DBException {
        try (DishesDao dao = daoFactory.createDishesDao()) {
            return dao.findById(id.intValue()).orElseThrow(
                    () -> new DBException(Prop.getDBProperty("select.dishes.dbe") + id));
        }
    }

    public List<DishesDTO> getAll(String locale) throws DBException {
        try (DishesDao dao = daoFactory.createDishesDao()) {
            return DishesDTOConverter.convertList(dao.findAll(), locale);
        }
    }

    public void update(Dishes dish) {
        try (DishesDao dao = daoFactory.createDishesDao()) {
            dao.update(dish);
        }
    }

    public void delete(Long id) {
        try (DishesDao dao = daoFactory.createDishesDao()) {
            dao.delete(id.intValue());
        }
    }

}
