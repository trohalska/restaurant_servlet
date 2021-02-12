package ua.servlet.restaurant.command;

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
        try {
            String json = ParseJsonBody.getBody(request);
            logger.info(json);
            if (json.equals("")) {
                return "/registration.jsp";
            }
            Logins logins = new ObjectMapper().readValue(json, Logins.class);
            if (!validateInput(logins, request)) {
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
        final String PASS_REGEX = "^[a-z0-9._%+-]{2,6}$";
        Matcher m;

        if (user.getLogin().equals("") || user.getEmail().equals("")) {
            return setErrorAndLog(request, "invalid.fields");
        }

        m = Pattern.compile(EMAIL_REGEX).matcher(user.getEmail());
        if (!m.find()) {
            return setErrorAndLog(request, "invalid.email");
        }

        m = Pattern.compile(PASS_REGEX).matcher(user.getPassword());
        if (!m.find()) {
            return setErrorAndLog(request, "invalid.password");
        }
        return true;
    }

    private boolean setErrorAndLog(HttpServletRequest request, String error) {
        String errorMsg = Prop.getDBProperty(error);
        request.setAttribute("errorMsg", errorMsg);
        logger.warn(errorMsg);
        return false;
    }

}
