package ua.servlet.restaurant.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.entity.Logins;
import ua.servlet.restaurant.service.LoginsService;
import ua.servlet.restaurant.utils.JsonBody;
import ua.servlet.restaurant.utils.Prop;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RegistrationController implements Command {
    Logger logger = LogManager.getLogger(Command.class);
    private final LoginsService loginsService;
    public RegistrationController() {
        this.loginsService = new LoginsService();
    }

    /**
     * Get json string as input data, parse it into Logins class and write to DB
     * Set errorMsg:
     * - if catch DBException
     * - if cannot get json or it is invalid
     * @param request request
     * @return registration page
     */
    @Override
    public String execute(HttpServletRequest request) {
        try {
            String json = JsonBody.getBody(request);
            logger.info(json);
            if (json.equals("")) {
                return "/registration.jsp";
            }
            Logins logins = new ObjectMapper().readValue(json, Logins.class);
            if (Validator.valid_Registration(logins, request)) {
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
            String errorMsg = Prop.getDBProperty("error.json");
            request.setAttribute("errorMsg", Prop.getDBProperty("error.try.again"));
            logger.error(errorMsg);
        }
        return "/registration.jsp";
    }

}
