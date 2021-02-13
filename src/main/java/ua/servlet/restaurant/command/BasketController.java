package ua.servlet.restaurant.command;

import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dto.BasketDTO;
import ua.servlet.restaurant.service.BasketService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class BasketController implements Command {
    private final BasketService basketService;
    public BasketController() {
        this.basketService = new BasketService();
    }

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        try {
            BasketDTO basket = basketService.getAll(request);
            request.setAttribute("dishes", basket.getDishes());
            request.setAttribute("totalPrice", basket.getTotalPrice());
        } catch (DBException e) {
            logger.info(e.getMessage());
            request.setAttribute("errorMsg", e.getMessage());
        }
        return "/WEB-INF/basket.jsp";
    }
}
