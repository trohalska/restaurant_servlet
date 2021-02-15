package ua.servlet.restaurant.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.entity.Logins;
import ua.servlet.restaurant.service.LoginsService;
import ua.servlet.restaurant.utils.ParseJsonBody;
import ua.servlet.restaurant.utils.Prop;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RegistrationController implements Command {
    Logger logger = LogManager.getLogger(Command.class);
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

            if (!Validator.valid_Registration(logins, request)) {
                return "/registration.jsp";
            }

            logger.info(Prop.getDBProperty("create.user.log") + logins.getLogin());
            logger.info(logins.toString());

            loginsService.create(logins);
        } catch (DBException e) {
            String errorMsg = Prop.getDBProperty("invalid.username");
            request.setAttribute("errorMsg", errorMsg);
            logger.warn(errorMsg);
        } catch (IOException e) {
            String errorMsg = "Cannot get json body";
            request.setAttribute("errorMsg", errorMsg);
            logger.error(errorMsg);
        }
        return "/registration.jsp";
    }

}
