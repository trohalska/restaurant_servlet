package ua.servlet.restaurant.dao;

import ua.servlet.restaurant.dao.entity.Baskets;

import java.util.List;
import java.util.Optional;

public interface BasketsDao extends GenericDao<Baskets> {
    Optional<List<Baskets>> findAllByLoginId(Long id) throws DBException;
    void deleteAllByLoginId(int id);
}
