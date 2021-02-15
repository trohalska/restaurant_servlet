package ua.servlet.restaurant.dao;

import ua.servlet.restaurant.dao.entity.Dishes;
import ua.servlet.restaurant.dto.Page;

public interface DishesDao extends GenericDao<Dishes> {
    Page findAllPageable(int pageNo, String sort, String direct, int categoryId, String locale) throws DBException;
}
