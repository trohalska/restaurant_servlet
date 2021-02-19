package ua.servlet.restaurant.command;

import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.entity.Logins;
import ua.servlet.restaurant.service.BasketService;
import ua.servlet.restaurant.utils.Prop;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Deleting one dish from user's basket.
 * Get dish id as input, make validation.
 * Get user from session and deleting dish from user's basket (if it really belows to this user).
 *
 * Set errorMsg if validation failed or cannot delete.
 */
public class BasketDeleteController implements Command {
    private final BasketService basketService;
    public BasketDeleteController() {
        this.basketService = new BasketService();
    }

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        String id = request.getParameter("id");
        Long login_id = CommandUtility.getPrincipal(request).getId();

        if (Validator.valid_ID(request, id)) {
            return "/WEB-INF/basket.jsp";
        }
        logger.info(Prop.getDBProperty("delete.basket.log") + id);
        try {
            basketService.delete(login_id, Long.parseLong(id));
        } catch (DBException e) {
            logger.info(e.getMessage());
            request.setAttribute("errorMsg", e.getMessage());
            return "/WEB-INF/basket.jsp";
        }
        return "redirect:/customer/basket";
    }

}
