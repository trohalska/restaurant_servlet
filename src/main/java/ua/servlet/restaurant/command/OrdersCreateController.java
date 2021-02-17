package ua.servlet.restaurant.command;

import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.entity.Logins;
import ua.servlet.restaurant.service.OrdersService;
import ua.servlet.restaurant.utils.Prop;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class OrdersCreateController implements Command {
    private final OrdersService ordersService;
    public OrdersCreateController() {
        this.ordersService = new OrdersService();
    }

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        Logins user = CommandUtility.getPrincipal(request);

        logger.info(Prop.getDBProperty("create.order.log") + user.getLogin());
        try {
            ordersService.create(user);
        } catch (DBException e) {
            logger.info(e.getMessage());
            request.setAttribute("errorMsg", e.getMessage());
        }
        return "redirect:/customer/orders";
    }

}
