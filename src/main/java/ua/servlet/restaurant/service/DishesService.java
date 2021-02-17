package ua.servlet.restaurant.service;

import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.DaoFactory;
import ua.servlet.restaurant.dao.DishesDao;
import ua.servlet.restaurant.dao.entity.Dishes;
import ua.servlet.restaurant.dto.DishesDTO;
import ua.servlet.restaurant.dto.Page;
import ua.servlet.restaurant.dto.converter.DishesDTOConverter;
import ua.servlet.restaurant.utils.Prop;

import java.util.List;

public class DishesService {
    DaoFactory daoFactory = DaoFactory.getInstance();

    // todo create, update
    public Dishes findById(Long id) throws DBException {
        try (DishesDao dao = daoFactory.createDishesDao()) {
            return dao.findById(id.intValue()).orElseThrow(
                    () -> new DBException(Prop.getDBProperty("select.dishes.dbe") + id));
        }
    }

    /**
     * Get page
     * @param locale for localization
     * @param pageNo current page
     * @param sort sort field
     * @param direct sort direction (ASC or DESC)
     * @param categoryId filter category
     * @return Page
     * @throws DBException if DB throw exception
     */
    public Page getAllPageable(String locale, int pageNo, String sort,
                               String direct, int categoryId) throws DBException {
        try (DishesDao dao = daoFactory.createDishesDao()) {
            return dao.findAllPageable(pageNo, sort, direct, categoryId, locale);
        }
    }

    @Deprecated
    public List<DishesDTO> getAll(String locale) throws DBException {
        try (DishesDao dao = daoFactory.createDishesDao()) {
            return DishesDTOConverter.convertList(dao.findAll(), locale);
        }
    }

    // todo create, update
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
