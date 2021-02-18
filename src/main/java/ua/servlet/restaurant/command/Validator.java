package ua.servlet.restaurant.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.servlet.restaurant.dao.entity.Logins;
import ua.servlet.restaurant.dao.entity.Status;
import ua.servlet.restaurant.utils.Prop;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static final Logger logger = LogManager.getLogger(Validator.class);
    private final static String LOGIN_REGEX = "^[a-zA-Z0-9]{2,}$";
    private final static String EMAIL_REGEX = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$";
    private final static String PASS_REGEX = "^[a-z0-9._%+-]{2,6}$";

    /**
     * Set errors into request and log it (with validation logger),
     * and returns true because it is invalid branch end
     * @param request request
     * @param error error string
     * @return true
     */
    static boolean setErrorAndLog(HttpServletRequest request, String error) {
        String errorMsg = Prop.getDBProperty(error);
        request.setAttribute("errorMsg", errorMsg);
        logger.warn(errorMsg);
        return true;
    }

    /**
     * Validation for null or empty fields
     * @param request request
     * @param args input fields
     * @return true if invalid, false if valid
     */
    static boolean valid_EmptyFields(HttpServletRequest request, String... args) {
        for (String s : args) {
            if (s == null || s.equals("")) {
                return setErrorAndLog(request, "invalid.fields");
            }
        }
        return false;
    }

    /**
     * Registration validation (for empty fields and with regex)
     * @param user input fields
     * @param request request
     * @return true if invalid, false if valid
     */
    static boolean valid_Registration(Logins user, HttpServletRequest request) {
        if (valid_EmptyFields(request, user.getLogin(), user.getEmail(), user.getPassword())) {
            return true;
        }
        Matcher m;
        m = Pattern.compile(LOGIN_REGEX).matcher(user.getLogin());
        if (!m.find()) {
            return setErrorAndLog(request, "invalid.login");
        }
        m = Pattern.compile(EMAIL_REGEX).matcher(user.getEmail());
        if (!m.find()) {
            return setErrorAndLog(request, "invalid.email");
        }
        m = Pattern.compile(PASS_REGEX).matcher(user.getPassword());
        if (!m.find()) {
            return setErrorAndLog(request, "invalid.password");
        }
        return false;
    }

    /**
     * Validation for manager confirmation (change order's statuses)
     * validation id and status
     * @param request request
     * @param args input fields
     * @return true if invalid, false if valid
     */
    static boolean valid_OrdersConfirm(HttpServletRequest request, String... args) {
        if (valid_EmptyFields(request, args) || valid_ID(request, args[0])) {
            return true;
        }
        Status status;
        try {
            status = Status.valueOf(args[1]);
        } catch (Exception e) {
            return setErrorAndLog(request, "invalid.status");
        }
        if (status.equals(Status.DONE) || status.equals(Status.NEW)) {
            return setErrorAndLog(request, "invalid.update.status");
        }
        return false;
    }

    /**
     * Validation id - not String, not equals zero and not negative number
     * @param request request
     * @param args String id
     * @return true if invalid, false if valid
     */
    static boolean valid_ID(HttpServletRequest request, String args) {
        if (valid_EmptyFields(request, args)) {
            return true;
        }
        long id;
        try {
            id = Long.parseLong(args);
        } catch (Exception e) {
            return setErrorAndLog(request, "invalid.id");
        }
        if (id <= 0) {
            return setErrorAndLog(request, "invalid.positive.id");
        }
        return false;
    }

}
