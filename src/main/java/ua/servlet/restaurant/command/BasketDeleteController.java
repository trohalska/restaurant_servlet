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
        String id = request.getParameter("id");
        if (Validator.valid_ID(request, id)) {
            return "/WEB_INF/basket.jsp";
        }
        logger.info(Prop.getDBProperty("delete.basket.log") + id);
        basketService.delete(Long.parseLong(id));
        return "redirect:/customer/basket";
    }

}
