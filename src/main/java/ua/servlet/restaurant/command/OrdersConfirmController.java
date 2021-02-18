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
 * Change order status when manager manage customers orders
 * Get order id and status as input, make validation.
 * Update order.
 *
 * Set errorMsg if validation failed or cannot update.
 */
public class OrdersConfirmController implements Command {
    private final OrdersService ordersService;
    public OrdersConfirmController() {
        this.ordersService = new OrdersService();
    }

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        String idStr = request.getParameter("id");
        String statusStr = request.getParameter("status");

        if (Validator.valid_OrdersConfirm(request, idStr, statusStr)) {
            return "/WEB-INF/manager/orders_manager.jsp";
        }

        logger.info(Prop.getDBProperty("update.orders.log") + idStr);
        try {
            ordersService.update(Orders.builder()
                    .id(Long.parseLong(idStr))
                    .status(Status.valueOf(statusStr))
                    .build());
        } catch (DBException e) {
            logger.info(e.getMessage());
            request.setAttribute("errorMsg", e.getMessage());
            return "/WEB-INF/manager/orders_manager.jsp";
        }
        return "redirect:/manager/orders_manager";
    }

}
