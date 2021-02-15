package ua.servlet.restaurant.command;

import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dto.DishesDTO;
import ua.servlet.restaurant.service.DishesService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class MainController implements Command {
    private final DishesService dishesService;
    public MainController() {
        this.dishesService = new DishesService();
    }
    // TODO sorting

    @Override
    public String execute(HttpServletRequest request) {
        String locale = CommandUtility.getLocale(request);

        // todo input validation

        String page = request.getParameter("page");
        String sort = request.getParameter("sort");
        String direct = request.getParameter("direct");
        String category = request.getParameter("category");

        int pageNo = 1;
        int categoryId = 0;
        if (!Validator.valid_EmptyFields(request, page, sort, direct, category)) {
            sort = "id";
            direct = "asc";
        } else {
            pageNo = Integer.parseInt(page);
            categoryId = Integer.parseInt(category);
        }

        pageNo = Integer.parseInt(page);

        List<DishesDTO> list;
        try {
            list = dishesService.getAll(locale, pageNo, sort, direct, categoryId);
        } catch (DBException e) {
            logger.info(e.getMessage());
            request.setAttribute("errorMsg", e.getMessage());
            return "/WEB-INF/menu.jsp";
        }
        request.setAttribute("dishes", list);
        request.setAttribute("pageNo", pageNo);
        request.setAttribute("totalPages", 3);
        return "/WEB-INF/menu.jsp";
    }
}
