package ua.servlet.restaurant.command;

import ua.servlet.restaurant.dao.entity.Dishes;
import ua.servlet.restaurant.service.DishesService;
import ua.servlet.restaurant.utils.Prop;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Update dish.
 * Get dish id, name_en, name_ua, price as input, make validation.
 *
 * Set errorMsg if validation failed.
 */
public class DishUpdateDoController implements Command  {
    private final DishesService dishesService;
    public DishUpdateDoController() {
        this.dishesService = new DishesService();
    }

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        String id = request.getParameter("id");
        String nameEn = request.getParameter("name_en");
        String nameUa = request.getParameter("name_ua");
        String priceS = request.getParameter("price");

        if (Validator.valid_ID(request, id)) {
            return "redirect:/manager/dishes_manager";
        } else if (Validator.valid_DishCreateUpdate(request, nameEn, nameUa, priceS)) {
            return "/WEB-INF/dish_update.jsp";
        }
        logger.info(Prop.getDBProperty("update.dishes.log") + nameEn);
        dishesService.update(Dishes.builder()
                .id(Long.parseLong(id))
                .nameEn(nameEn)
                .nameUa(nameUa)
                .price(new BigDecimal(priceS))
                .build());
        return "redirect:/manager/dishes_manager";
    }
}

