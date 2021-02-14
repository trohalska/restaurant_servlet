package ua.servlet.restaurant.dao;

import ua.servlet.restaurant.dao.entity.Orders;

import java.util.List;
import java.util.Optional;

public interface OrdersDao extends GenericDao<Orders> {
    Optional<List<Orders>> findAllByLoginId(Long id) throws DBException;
}
