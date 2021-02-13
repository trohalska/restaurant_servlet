package ua.servlet.restaurant.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BasketController implements Command {
    private final Map<String, Command> commands = new HashMap<>();
    public BasketController() {
        commands.put("", new BasketShow());
        commands.put("add", new BasketAdd());
//        commands.put("delete", new BasketDelete());
//        commands.put("deleteAll", new BasketDeleteAll());
    }

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {

        String path = request.getRequestURI().replaceAll(".*/app/customer/basket/" , "");
        logger.info(path);

        Command command = commands.getOrDefault(path, (r)->"/index.jsp"); // basket.execute with get all
        return command.execute(request);

    }
}
