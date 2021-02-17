package ua.servlet.restaurant.command;

import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dto.Page;
import ua.servlet.restaurant.service.DishesService;

import javax.servlet.http.HttpServletRequest;

public class MainController implements Command {
    private final DishesService dishesService;
    public MainController() {
        this.dishesService = new DishesService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String locale = CommandUtility.getLocale(request);

        String page = request.getParameter("page");
        String sort = request.getParameter("sort");
        String direct = request.getParameter("direct");
        String category = request.getParameter("category");

        int pageNo = (Validator.valid_EmptyFields(request, page))          ? 1     : Integer.parseInt(page);
        int categoryId = (Validator.valid_EmptyFields(request, category))  ? 0     : Integer.parseInt(category);
        sort = (Validator.valid_EmptyFields(request, sort))                ? "id"  : sort;
        direct = (Validator.valid_EmptyFields(request, direct))            ? "ASC" : direct;

        Page pageable;
        try {
            pageable = dishesService.getAllPageable(locale, pageNo, sort, direct, categoryId);
        } catch (DBException e) {
            logger.info(e.getMessage());
            request.setAttribute("errorMsg", e.getMessage());
            return "/WEB-INF/menu.jsp";
        }
        request.setAttribute("dishes", pageable.getDishes());
        request.setAttribute("categories", pageable.getCategories());
        request.setAttribute("pageNo", pageNo);
        request.setAttribute("totalPages", pageable.getTotalPages());
        request.setAttribute("sort", sort);
        request.setAttribute("direct", direct);
        request.setAttribute("directTable", (direct.equals("ASC")) ? "DESC" : "ASC");
        request.setAttribute("name", (locale.equals("en")) ? "name_en" : "name_ua");
        request.setAttribute("category", categoryId);
        return "/WEB-INF/menu.jsp";
    }
}
