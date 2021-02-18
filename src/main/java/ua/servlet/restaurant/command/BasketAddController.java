package ua.servlet.restaurant.command;

import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.entity.Logins;
import ua.servlet.restaurant.service.BasketService;
import ua.servlet.restaurant.utils.Prop;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Adding dish to basket.
 * Get dish id as input, make validation.
 * Get user from session and add dish to user's basket.
 *
 * Set errorMsg if validation failed or cannot add dish (dish id does not exist in DB).
 */
public class BasketAddController implements Command {
    private final BasketService basketService;
    public BasketAddController() {
        this.basketService = new BasketService();
    }

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        String id = request.getParameter("id");
        if (Validator.valid_ID(request, id)) {
            return "/WEB-INF/basket.jsp";
        }
        Logins user = CommandUtility.getPrincipal(request);
        logger.info(Prop.getDBProperty("create.basket.log") + id);
        try {
            basketService.create(user, Long.parseLong(id));
        } catch (DBException e) {
            logger.info(e.getMessage());
            request.setAttribute("errorMsg", e.getMessage());
            return "/WEB-INF/basket.jsp";
        }
        return "redirect:/customer/basket";
    }

}
