package ua.servlet.restaurant.command;

import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.entity.Dishes;
import ua.servlet.restaurant.service.DishesService;
import ua.servlet.restaurant.utils.Prop;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Show dish update page for manager.
 * Get dish id as input, make validation.
 * Get user and locale from session.
 * Get basket list from DB and count total price.
 *
 * Set errorMsg if cannot get basket list or if basket is empty.
 */
public class DishUpdateController implements Command {
    private final DishesService dishesService;
    public DishUpdateController() {
        this.dishesService = new DishesService();
    }

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        String id = request.getParameter("id");
        if (Validator.valid_ID(request, id)) {
            return "/WEB-INF/dish_update.jsp";
        }
        logger.info(Prop.getDBProperty("select.dishes.log") + id);
        try {
            Dishes dish = dishesService.findById(Long.parseLong(id));
            request.setAttribute("dish", dish);
        } catch (DBException e) {
            logger.warn(e.getMessage());
            request.setAttribute("errorMsg", e.getMessage());
        }
        return "/WEB-INF/dish_update.jsp";
    }
}
