package ua.servlet.restaurant.command;

import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.entity.Dishes;
import ua.servlet.restaurant.service.DishesService;
import ua.servlet.restaurant.utils.Prop;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;

public class MenuUpdateController implements Command  {
    private final DishesService dishesService;
    public MenuUpdateController() {
        this.dishesService = new DishesService();
    }

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        Long id = Long.parseLong(request.getParameter("id"));

        if (id <= 0 ) {
            request.setAttribute("errorMsg", Prop.getDBProperty("invalid.fields"));
            return "/WEB-INF/menu.jsp";
        }

        logger.info(Prop.getDBProperty("select.dishes.log") + id);
        try {
            Dishes dish = dishesService.findById(id);
            request.setAttribute("dish", dish);
        } catch (DBException e) {
            logger.warn(e.getMessage());
            return "redirect:/app/main";
        }
        return "/WEB-INF/manager/dish_update.jsp";
    }
}
