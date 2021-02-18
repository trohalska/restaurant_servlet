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

    /**
     * Get Page of dishes for main page
     * @param locale locale
     * @param pageNo current page
     * @param sort sort field
     * @param direct sort direction (ASC or DESC)
     * @param categoryId filter bu category
     * @return Page
     * @throws DBException if cannot get page
     */
    public Page getAllPageable(String locale, int pageNo, String sort,
                               String direct, int categoryId) throws DBException {
        try (DishesDao dao = daoFactory.createDishesDao()) {
            return dao.findAllPageable(pageNo, sort, direct, categoryId, locale);
        }
    }

    /**
     * Get all dishes for dishes manager page
     * @param locale for internationalization
     * @return List<DishesDTO>
     * @throws DBException if cannot find list or list is empty
     */
    public List<DishesDTO> getAll(String locale) throws DBException {
        try (DishesDao dao = daoFactory.createDishesDao()) {
            return DishesDTOConverter.convertList(dao.findAll().orElseThrow(
                    () -> new DBException(Prop.getDBProperty("select.all.dishes.empty"))
            ), locale);
        }
    }

    /**
     * Get dish for dish update page
     * @param id dish id
     * @return dish
     * @throws DBException if cannot find
     */
    public Dishes findById(Long id) throws DBException {
        try (DishesDao dao = daoFactory.createDishesDao()) {
            return dao.findById(id.intValue()).orElseThrow(
                    () -> new DBException(Prop.getDBProperty("select.dishes.dbe") + id));
        }
    }

    /**
     * Create new dish (manager)
     * @param dishes dish
     * @return Dishes
     * @throws DBException if cannot create
     */
    public Dishes create(Dishes dishes) throws DBException {
        try (DishesDao dao = daoFactory.createDishesDao()) {
            return dao.create(dishes).orElseThrow(
                    () -> new DBException(
                            Prop.getDBProperty("create.dishes.dbe"))
            );
        }
    }

    /**
     * Update dish (manager)
     * @param dish Dish
     */
    public void update(Dishes dish) {
        try (DishesDao dao = daoFactory.createDishesDao()) {
            dao.update(dish);
        }
    }

    /**
     * Delete dish (manager)
     * @param login_id manager id
     * @param id dish id
     * @throws DBException if cannot delete
     */
    public void delete(Long login_id, Long id) throws DBException {
        try (DishesDao dao = daoFactory.createDishesDao()) {
            dao.delete(login_id, id);
        }
    }

}
