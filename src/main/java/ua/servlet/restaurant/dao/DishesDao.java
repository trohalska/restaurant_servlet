package ua.servlet.restaurant.dao;

import ua.servlet.restaurant.dao.entity.Dishes;

import java.util.List;

public interface DishesDao extends GenericDao<Dishes> {
    List<Dishes> findAllPageable(int pageNo, String sort, String direct, int categoryId) throws DBException;
}
