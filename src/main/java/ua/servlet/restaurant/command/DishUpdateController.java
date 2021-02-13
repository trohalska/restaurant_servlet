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

        if ( nameEn == null || nameEn.equals("")
                || nameUa == null || nameUa.equals("")
                || priceS == null || priceS.equals("")) {
            request.setAttribute("errorMsg", Prop.getDBProperty("invalid.fields"));
            return "/WEB-INF/manager/dish_update.jsp";
        }

        BigDecimal price = new BigDecimal(priceS);

        if ( price.compareTo(BigDecimal.ZERO) <= 0) {
            request.setAttribute("errorMsg", Prop.getDBProperty("invalid.price"));
            return "/WEB-INF/manager/dish_update.jsp";
        }

        logger.info(Prop.getDBProperty("update.dishes.log") + nameEn);
        dishesService.update(Dishes.builder()
                .nameEn(nameEn)
                .nameUa(nameUa)
                .price(price)
                .build());
        return "/WEB-INF/manager/dish_update.jsp";
    }
}
