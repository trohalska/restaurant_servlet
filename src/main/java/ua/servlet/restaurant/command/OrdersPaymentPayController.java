package ua.servlet.restaurant.command;

import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.entity.Orders;
import ua.servlet.restaurant.dao.entity.Status;
import ua.servlet.restaurant.service.OrdersService;
import ua.servlet.restaurant.utils.Prop;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class OrdersPaymentPayController implements Command {
    private final OrdersService ordersService;
    public OrdersPaymentPayController() {
        this.ordersService = new OrdersService();
    }

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        Long id = Long.parseLong(request.getParameter("id"));

        logger.info(Prop.getDBProperty("update.orders.log") + id);
        try {
            ordersService.update(Orders.builder()
                    .id(id)
                    .status(Status.PAYED)
                    .build());
        } catch (DBException e) {
            logger.info(e.getMessage());
            request.setAttribute("errorMsg", e.getMessage());
            return "/WEB_INF/payment.jsp";
        }
        return "redirect:/customer/orders";
    }
}
