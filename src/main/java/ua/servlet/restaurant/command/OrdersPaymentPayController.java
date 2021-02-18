package ua.servlet.restaurant.command;

import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.entity.Orders;
import ua.servlet.restaurant.dao.entity.Status;
import ua.servlet.restaurant.service.OrdersService;
import ua.servlet.restaurant.utils.Prop;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Change order status when customer pays for his order.
 * Get order id as input, make validation.
 * Update order, set status PAYED.
 *
 * Set errorMsg if validation failed or cannot update.
 */
public class OrdersPaymentPayController implements Command {
    private final OrdersService ordersService;
    public OrdersPaymentPayController() {
        this.ordersService = new OrdersService();
    }

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        // todo get principal, user cannot pay if order isn't his
        String id = request.getParameter("id");
        if (Validator.valid_ID(request, id)) {
            return "/WEB-INF/payment.jsp";
        }
        logger.info(Prop.getDBProperty("update.orders.log") + id);
        try {
            ordersService.update(Orders.builder()
                    .id(Long.parseLong(id))
                    .status(Status.NEW)
                    .build());
        } catch (DBException e) {
            logger.info(e.getMessage());
            request.setAttribute("errorMsg", e.getMessage());
            return "/WEB-INF/payment.jsp";
        }
        return "redirect:/customer/orders";
    }
}
