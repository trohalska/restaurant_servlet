package ua.servlet.restaurant.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.entity.Logins;
import ua.servlet.restaurant.service.LoginsService;
import ua.servlet.restaurant.utils.ParseJsonBody;
import ua.servlet.restaurant.utils.Prop;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            String json = ParseJsonBody.getBody(request);
//            System.out.println(json);
            if (json.equals("")) {
                return "/registration.jsp";
            }
            Logins logins = new ObjectMapper().readValue(json, Logins.class);
            if (!validateInput(logins, request)) {
                String errorMsg = "Invalid email";
                request.setAttribute("errorMsg", errorMsg);
                logger.error(errorMsg);
                return "/registration.jsp";
            }

            logger.info(Prop.getDBProperty("create.user.log") + logins.getLogin());
            logger.info(logins.toString());

            loginsService.create(logins);
        } catch (DBException e) {
            String errorMsg = Prop.getDBProperty("select.login.byLogin.dbe.exist");
            request.setAttribute("errorMsg", errorMsg);
            logger.warn(errorMsg);
        } catch (IOException e) {
            String errorMsg = "Cannot get json body";
            request.setAttribute("errorMsg", errorMsg);
            logger.error(errorMsg);
        }
        return "/registration.jsp";
    }

    private boolean validateInput(Logins user, HttpServletRequest request) {
        final String EMAIL_REGEX = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$";

        Matcher m = Pattern.compile(EMAIL_REGEX).matcher(user.getEmail());
        if (!m.find()) {
            return false;
        }
        return true;
    }

}
