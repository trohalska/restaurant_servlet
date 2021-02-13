package ua.servlet.restaurant.command;

import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dto.DishesDTO;
import ua.servlet.restaurant.dto.DishesDTOMapper;
import ua.servlet.restaurant.filters.SessionLocaleFilter;
import ua.servlet.restaurant.service.BasketService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public class BasketShow implements Command {
    private final BasketService basketService;
    public BasketShow() {
        this.basketService = new BasketService();
    }

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
//        try {
//            List<BasketDTO> list =
//                    DishesDTOMapper.convertList(
//                            dishesService.getAll(),
//                            SessionLocaleFilter.getLocale(request));
//            request.setAttribute("dishes", list);
//        } catch (DBException e) {
//            logger.info(e.getMessage());
//            request.setAttribute("errorMsg", e.getMessage());
//        }
    }
}
