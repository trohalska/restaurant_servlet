package ua.servlet.restaurant.command;

import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.entity.Logins;
import ua.servlet.restaurant.dao.entity.Orders;
import ua.servlet.restaurant.service.OrdersService;
import ua.servlet.restaurant.utils.Prop;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public class OrdersController implements Command {
    private final OrdersService ordersService;
    public OrdersController() {
        this.ordersService = new OrdersService();
    }

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        Logins user = CommandUtility.getPrincipal(request);

        logger.info(Prop.getDBProperty("select.all.orders.log"));
        try {
            List<Orders> orders =
                    ordersService.getAllByLoginId(user.getId());
            request.setAttribute("orders", orders);
        } catch (DBException e) {
            logger.warn(e.getMessage());
            request.setAttribute("errorMsg", e.getMessage());
        }
        return "/WEB-INF/orders.jsp";
    }
}
