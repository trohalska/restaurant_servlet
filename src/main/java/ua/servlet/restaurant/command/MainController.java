package ua.servlet.restaurant.command;

import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dto.DishesDTO;
import ua.servlet.restaurant.dto.DishesDTOMapper;
import ua.servlet.restaurant.filters.SessionLocaleFilter;
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
        try {
            List<DishesDTO> list =
                    DishesDTOMapper.convertList(
                            dishesService.getAll(),
                            SessionLocaleFilter.getLocale(request));
            request.setAttribute("dishes", list);
        } catch (DBException e) {
            logger.info(e.getMessage());
            request.setAttribute("errorMsg", e.getMessage());
        }

        return "/WEB-INF/menu.jsp";
    }
}
