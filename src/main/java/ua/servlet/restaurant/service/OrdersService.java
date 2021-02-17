package ua.servlet.restaurant.service;

import ua.servlet.restaurant.dao.*;
import ua.servlet.restaurant.dao.entity.*;
import ua.servlet.restaurant.utils.Prop;

import java.util.List;

public class OrdersService {
    DaoFactory daoFactory = DaoFactory.getInstance();

    /**
     * Find order by id for payment page
     * @param id order id
     * @return Orders
     * @throws DBException if DB throw exception
     */
    public Orders findById(Long id) throws DBException {
        try (OrdersDao dao = daoFactory.createOrdersDao()) {
            return dao.findById(id.intValue()).orElseThrow(
                    () -> new DBException(Prop.getDBProperty("select.orders.dbe") + id));
        }
    }

    /**
     * Find all user orders for user order's page
     * @param id order id
     * @return orders list
     * @throws DBException if DB throw exception
     */
    public List<Orders> getAllByLoginId(Long id) throws DBException {
        List<Orders> list;
        try (OrdersDao dao = daoFactory.createOrdersDao()) {
            list = dao.findAllByLoginId(id).orElseThrow(
                    () -> new DBException(Prop.getDBProperty("select.all.orders.empty")));
        }
        return list;
    }

    /**
     * Find all orders for manager page
     * @return orders list
     * @throws DBException if DB throw exception or there are no orders
     */
    public List<Orders> getAll() throws DBException {
        List<Orders> list;
        try (OrdersDao dao = daoFactory.createOrdersDao()) {
            list = dao.findAll();
        }
        if (list.isEmpty()) {
            throw new DBException(Prop.getDBProperty("select.all.orders.empty"));
        }
        return list;
    }

    /**
     * Create new order
     * @param user user - owner of order
     * @throws DBException if DB throw exception
     */
    public void create(Logins user) throws DBException {
        try (OrdersDao dao = daoFactory.createOrdersDao()) {
            dao.create(Orders.builder()
                    .status(Status.NEW)
                    .login(user)
                    .build())
                    .orElseThrow(
                            () -> new DBException(Prop.getDBProperty("create.order.dbe")));
        }
    }

    /**
     * Update for payment and change status by manager
     * @param order Orders (for order id and status)
     * @throws DBException  if DB throw exception
     */
    public void update(Orders order) throws DBException {
        try (OrdersDao dao = daoFactory.createOrdersDao()) {
            dao.update(order);
        }
    }

}
