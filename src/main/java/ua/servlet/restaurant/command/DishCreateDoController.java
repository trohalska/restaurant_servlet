package ua.servlet.restaurant.command;

import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.entity.Categories;
import ua.servlet.restaurant.dao.entity.Dishes;
import ua.servlet.restaurant.service.DishesService;
import ua.servlet.restaurant.utils.Prop;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Create new dish.
 * Get dish name_en, name_ua, price as input, make validation.
 *
 * Set errorMsg if validation failed or cannot create.
 */
public class DishCreateDoController implements Command  {
    private final DishesService dishesService;
    public DishCreateDoController() {
        this.dishesService = new DishesService();
    }

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        String nameEn = request.getParameter("name_en");
        String nameUa = request.getParameter("name_ua");
        String priceS = request.getParameter("price");
        String category = request.getParameter("category");

        if (Validator.valid_DishCreateUpdate(request, nameEn, nameUa, priceS)
                || Validator.valid_ID(request, category)) {
            return "/WEB-INF/dish_create.jsp";
        }
        logger.info(Prop.getDBProperty("create.dishes.log") + nameEn);
        try {
            dishesService.create(Dishes.builder()
                    .nameEn(nameEn)
                    .nameUa(nameUa)
                    .price(new BigDecimal(priceS))
                    .category(Categories.builder().id(Long.parseLong(category)).build())
                    .build());
        } catch (DBException e) {
            logger.warn(e.getMessage());
            request.setAttribute("errorMsg", e.getMessage());
            return "/WEB-INF/dish_create.jsp";
        }

        return "redirect:/manager/dishes_manager";
    }
}

