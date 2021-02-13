package ua.servlet.restaurant.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class BasketAdd implements Command {
    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        return null;
    }

}
