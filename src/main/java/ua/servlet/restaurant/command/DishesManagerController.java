package ua.servlet.restaurant.command;

import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dto.DishesDTO;
import ua.servlet.restaurant.dto.Page;
import ua.servlet.restaurant.service.DishesService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Get internationalized page of dishes for main menu.
 * Get current page, sort field, sort direction, filter category if as input, make validation.
 *
 * Set errorMsg if validation failed or cannot find list (input invalid) or list is empty.
 */
public class DishesManagerController implements Command {
    private final DishesService dishesService;
    public DishesManagerController() {
        this.dishesService = new DishesService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String locale = CommandUtility.getLocale(request);

        try {
            List<DishesDTO> dishes = dishesService.getAll(locale);
            request.setAttribute("dishes", dishes);
        } catch (DBException e) {
            logger.info(e.getMessage());
            request.setAttribute("errorMsg", e.getMessage());
        }
        return "/WEB-INF/dishes_manager.jsp";
    }
}
