package ua.servlet.restaurant.command;

import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.entity.Dishes;
import ua.servlet.restaurant.dto.CategoriesDTO;
import ua.servlet.restaurant.service.CategoriesService;
import ua.servlet.restaurant.service.DishesService;
import ua.servlet.restaurant.utils.Prop;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class DishCreateController implements Command  {
    private final CategoriesService categoriesService;
    public DishCreateController() {
        this.categoriesService = new CategoriesService();
    }

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        String locale = CommandUtility.getLocale(request);

        logger.info(Prop.getDBProperty("select.all.categories.log"));
        try {
            List<CategoriesDTO> categories = categoriesService.getAll(locale);
            request.setAttribute("categories", categories);
        } catch (DBException e) {
            logger.warn(e.getMessage());
            request.setAttribute("errorMsg", e.getMessage());
        }
        return "/WEB-INF/dish_create.jsp";
    }
}

