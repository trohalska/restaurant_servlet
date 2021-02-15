package ua.servlet.restaurant.service;

import ua.servlet.restaurant.command.CommandUtility;
import ua.servlet.restaurant.dao.*;
import ua.servlet.restaurant.dao.entity.*;
import ua.servlet.restaurant.dto.BasketDTO;
import ua.servlet.restaurant.dto.DishesDTO;
import ua.servlet.restaurant.dto.converter.BasketDTOConverter;
import ua.servlet.restaurant.dto.converter.DishesDTOConverter;
import ua.servlet.restaurant.utils.Prop;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

// TODO get for manager
// TODO confirm

public class OrdersService {
    DaoFactory daoFactory = DaoFactory.getInstance();

    // for payment
    public Orders findById(Long id) throws DBException {
        try (OrdersDao dao = daoFactory.createOrdersDao()) {
            return dao.findById(id.intValue()).orElseThrow(
                    () -> new DBException(Prop.getDBProperty("select.orders.dbe") + id));
        }
    }

    // for user
    public List<Orders> getAllByLoginId(HttpServletRequest request) throws DBException {
//        String locale = CommandUtility.getLocale(request);
        Logins user = CommandUtility.getPrincipal(request);
        List<Orders> list;

        try (OrdersDao dao = daoFactory.createOrdersDao()) {
            list = dao.findAllByLoginId(user.getId()).orElseThrow(
                    () -> new DBException(Prop.getDBProperty("select.all.orders.empty")));
        }

        return list;
    }

    // for manager
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

    public void create(HttpServletRequest request, Logins user) throws DBException {
        try (OrdersDao dao = daoFactory.createOrdersDao()) {
            dao.create(Orders.builder()
                    .status(Status.NEW)
                    .login(user)
                    .build())
                    .orElseThrow(
                            () -> new DBException(Prop.getDBProperty("create.order.dbe")));
        }
    }

    public void update(Orders order) throws DBException {
        try (OrdersDao dao = daoFactory.createOrdersDao()) {
            dao.update(order);
        }
    }

//    public void delete(Long id) {
//        try (BasketsDao dao = daoFactory.createBasketsDao()) {
//            dao.delete(id.intValue());
//        }
//    }

}
