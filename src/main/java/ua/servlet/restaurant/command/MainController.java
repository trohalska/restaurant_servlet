package ua.servlet.restaurant.command;

import ua.servlet.restaurant.dao.entity.Dishes;
import ua.servlet.restaurant.service.DishesService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class MainController implements Command {
    private final DishesService dishesService;
    public MainController() {
        this.dishesService = new DishesService();
    }

    @Override
    public String execute(HttpServletRequest request) {

        List<Dishes> list = dishesService.getAll();
        request.setAttribute("dishes", list);

        return "/WEB-INF/menu.jsp";
    }
}
