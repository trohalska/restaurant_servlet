package ua.servlet.restaurant.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogOutController implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "redirect:/index.jsp";
    }
}
