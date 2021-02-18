package ua.servlet.restaurant.command;

import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.entity.Orders;
import ua.servlet.restaurant.service.OrdersService;
import ua.servlet.restaurant.utils.Prop;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Return payment page.
 * Get order id as input, make validation.
 * Get order from DB for payment page
 *
 * Set errorMsg if validation failed or cannot get order.
 */
public class OrdersPaymentController implements Command {
    private final OrdersService ordersService;
    public OrdersPaymentController() {
        this.ordersService = new OrdersService();
    }

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        // todo get principal, user cannot pay if order isn't his
        String id = request.getParameter("id");
        if (Validator.valid_ID(request, id)) {
            return "/WEB-INF/payment.jsp";
        }
        logger.info(Prop.getDBProperty("select.orders.log") + id);
        try {
            Orders order = ordersService.findById(Long.parseLong(id));
            request.setAttribute("order", order);
        } catch (DBException e) {
            logger.info(e.getMessage());
            request.setAttribute("errorMsg", e.getMessage());
        }
        return "/WEB-INF/payment.jsp";
    }
}
