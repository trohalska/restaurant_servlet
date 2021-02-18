package ua.servlet.restaurant.command;

import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.entity.Orders;
import ua.servlet.restaurant.service.OrdersService;
import ua.servlet.restaurant.utils.Prop;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Show orders list for manager.
 *
 * Set errorMsg if cannot get orders list  or if this list is empty.
 */
public class OrdersManagerController implements Command {
    private final OrdersService ordersService;
    public OrdersManagerController() {
        this.ordersService = new OrdersService();
    }

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        logger.info(Prop.getDBProperty("select.all.orders.manager.log"));
        try {
            List<Orders> orders = ordersService.getAll();
            request.setAttribute("orders", orders);
        } catch (DBException e) {
            logger.warn(e.getMessage());
            request.setAttribute("errorMsg", e.getMessage());
        }
        return "/WEB-INF/orders_manager.jsp";
    }
}
