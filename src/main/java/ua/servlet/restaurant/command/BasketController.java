package ua.servlet.restaurant.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class BasketController implements Command {

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {

        String path = request.getRequestURI().replaceAll(".*/app/customer/basket/" , "");
        logger.info(path);
        return null;
    }
}
