package ua.servlet.restaurant.command;

import ua.servlet.restaurant.service.DishesService;
import ua.servlet.restaurant.utils.Prop;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class MenuDeleteController implements Command {
    private final DishesService dishesService;
    public MenuDeleteController() {
        this.dishesService = new DishesService();
    }

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        String id = request.getParameter("id");
        if (!Validator.valid_ID(request, id)) {
            return "/WEB_INF/menu.jsp";
        }
        logger.info(Prop.getDBProperty("delete.dishes.log") + id);
        dishesService.delete(Long.parseLong(id));
        return "redirect:/app/menu";
    }
}
