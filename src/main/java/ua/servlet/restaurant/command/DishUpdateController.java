package ua.servlet.restaurant.command;

import ua.servlet.restaurant.dao.entity.Dishes;
import ua.servlet.restaurant.service.DishesService;
import ua.servlet.restaurant.utils.Prop;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;

public class DishUpdateController implements Command  {
    private final DishesService dishesService;
    public DishUpdateController() {
        this.dishesService = new DishesService();
    }

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        String nameEn = request.getParameter("name_en");
        String nameUa = request.getParameter("name_ua");
        String priceS = request.getParameter("price");

        if (!Validator.valid_DishUpdate(request, nameEn, nameUa, priceS)) {
            return "/WEB-INF/manager/dish_update.jsp";
        }
        logger.info(Prop.getDBProperty("update.dishes.log") + nameEn);
        dishesService.update(Dishes.builder()
                .nameEn(nameEn)
                .nameUa(nameUa)
                .price(new BigDecimal(priceS))
                .build());
        return "/WEB-INF/dish_update.jsp";
    }
}
