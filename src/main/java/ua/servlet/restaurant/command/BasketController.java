package ua.servlet.restaurant.command;

import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.entity.Logins;
import ua.servlet.restaurant.dto.BasketDTO;
import ua.servlet.restaurant.service.BasketService;
import ua.servlet.restaurant.utils.Prop;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Show basket list of dishes for user.
 * Get user and locale from session.
 * Get basket list from DB and count total price.
 *
 * Set errorMsg if cannot get basket list or if basket is empty.
 */
public class BasketController implements Command {
    private final BasketService basketService;
    public BasketController() {
        this.basketService = new BasketService();
    }

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        String locale = CommandUtility.getLocale(request);
        Logins user = CommandUtility.getPrincipal(request);

        logger.info(Prop.getDBProperty("select.all.baskets.log") + user.getLogin());
        try {
            BasketDTO basket = basketService.getAll(user, locale);
            request.setAttribute("dishes", basket.getDishes());
            request.setAttribute("totalPrice", basket.getTotalPrice());
        } catch (DBException e) {
            logger.info(e.getMessage());
            request.setAttribute("errorMsg", e.getMessage());
        }
        return "/WEB-INF/basket.jsp";
    }
}
