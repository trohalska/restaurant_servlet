package ua.servlet.restaurant.command;

import ua.servlet.restaurant.service.BasketService;
import ua.servlet.restaurant.utils.Prop;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class BasketDeleteController implements Command {
    private final BasketService basketService;
    public BasketDeleteController() {
        this.basketService = new BasketService();
    }

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        Long id = Long.parseLong(request.getParameter("id"));

        logger.info(Prop.getDBProperty("delete.basket.log") + id);
        basketService.delete(id);
        return "redirect:/app/customer/basket";
    }

}
