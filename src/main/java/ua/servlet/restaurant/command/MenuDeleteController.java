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
        Long id = Long.parseLong(request.getParameter("id"));

        logger.info(Prop.getDBProperty("delete.dishes.log") + id);
        dishesService.delete(id);
        return "redirect:/app/menu";
    }
}
