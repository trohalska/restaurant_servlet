package ua.servlet.restaurant.command;

import javax.servlet.http.HttpServletRequest;

public class ExceptionController implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        throw new RuntimeException("Generated exception");
    }
}
