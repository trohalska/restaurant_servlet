package ua.servlet.restaurant.command;

import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.service.BasketService;
import ua.servlet.restaurant.utils.Prop;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

public class BasketAddController implements Command {
    private final BasketService basketService;
    public BasketAddController() {
        this.basketService = new BasketService();
    }

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        Long id = Long.parseLong(request.getParameter("id"));

        logger.info(Prop.getDBProperty("create.basket.log") + id);
        try {
            basketService.create(request, id);
        } catch (DBException e) {
            logger.info(e.getMessage());
            request.setAttribute("errorMsg", e.getMessage());
        }
        return "redirect:/app/menu";
    }

//    public ResponseEntity<Baskets> add (@Valid @RequestBody ItemDTO itemDTO) {
//        log.info(Constants.ADD_NEW_DISH + itemDTO.toString());
//        try {
//            return ResponseEntity.ok(
//                    basketsService.saveNewItem(itemDTO));
//        } catch (Exception e) {
//            log.warn(e.getMessage());
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
//        }
//    }

}