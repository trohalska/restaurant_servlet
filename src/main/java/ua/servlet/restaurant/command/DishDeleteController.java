
package ua.servlet.restaurant.command;

import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.entity.Logins;
import ua.servlet.restaurant.dao.entity.RoleType;
import ua.servlet.restaurant.service.DishesService;
import ua.servlet.restaurant.utils.Prop;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Deleting dish from menu.
 * Get dish id as input, make validation.
 * Get user from session and deleting dish if user is manager.
 *
 * Set errorMsg if validation failed or cannot delete.
 */
public class DishDeleteController implements Command {
    private final DishesService dishesService;
    public DishDeleteController() {
        this.dishesService = new DishesService();
    }

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        String id = request.getParameter("id");
        Logins user = CommandUtility.getPrincipal(request);
        if (Validator.valid_ID(request, id)) {
            return "/WEB-INF/dish_update.jsp";
        }
        logger.info(Prop.getDBProperty("delete.dishes.log") + id);
        try {
            if (!user.getRole().equals(RoleType.ROLE_MANAGER)) {
                throw new DBException();
            }
            dishesService.delete(user.getId(), Long.parseLong(id));
        } catch (DBException e) {
            String errorMsg = Prop.getDBProperty("error.denied");
            logger.warn(errorMsg);
            request.setAttribute("errorMsg", errorMsg);
            return "/WEB-INF/dish_update.jsp";
        }
        return "redirect:/manager/dishes_manager";
    }
}