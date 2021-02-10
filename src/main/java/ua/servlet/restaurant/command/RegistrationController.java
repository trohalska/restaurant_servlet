package ua.servlet.restaurant.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import ua.servlet.restaurant.dao.entity.Logins;
import ua.servlet.restaurant.service.LoginsService;
import ua.servlet.restaurant.utils.ParseJsonBody;
import ua.servlet.restaurant.utils.Prop;

import javax.servlet.http.HttpServletRequest;

public class RegistrationController implements Command {
    private final LoginsService loginsService;
    public RegistrationController() {
        this.loginsService = new LoginsService();
    }

    @Override
    public String execute(HttpServletRequest request) {
//        String login = request.getParameter("login");
//        String pass = request.getParameter("password");
//        String email = request.getParameter("email");
        try {
//            String json = request.getParameter("data");
            String json = ParseJsonBody.getBody(request);
//            System.out.println(json);
            Logins logins = new ObjectMapper().readValue(json, Logins.class);

            logger.info(Prop.getDBProperty("create.user.log") + logins.getLogin());
            logger.info(logins.toString());

            loginsService.create(logins);
        } catch (Exception e) {
//            e.toString();
            // TODO error msg in request
            request.setAttribute("errorMsg", "User with same login exists!");
            logger.error("User with same login exists!");
        }
        return "/registration.jsp";
    }

}
