package ua.servlet.restaurant.command;

import ua.servlet.restaurant.service.BasketService;
import ua.servlet.restaurant.utils.Prop;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Deleting all dishes from user's basket.
 * Get user id from session and deleting all dishes from user's basket.
 *
 * DO NOT set errorMsg if cannot delete all, just log it in JDBC.
 */
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
        return "redirect:/customer/basket";
    }

}
