package ua.servlet.restaurant.command;

import ua.servlet.restaurant.service.BasketService;
import ua.servlet.restaurant.utils.Prop;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class BasketDeleteAllController implements Command {
    private final BasketService basketService;
    public BasketDeleteAllController() {
        this.basketService = new BasketService();
    }

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        Long id = CommandUtility.getPrincipal(request).getId();

        logger.info(Prop.getDBProperty("delete.all.basket.log") + id);
        basketService.deleteAll(id);
        return "redirect:/app/customer/basket";
    }

}
