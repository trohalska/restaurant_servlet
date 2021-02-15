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
    private final static String EMAIL_REGEX = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$";
    private final static String PASS_REGEX = "^[a-z0-9._%+-]{2,6}$";
    private final static String ID_REGEX = "^[1-9]\\d*$";
    private final static String PRICE_REGEX = "^\\d+(,\\d{1,2})?$";

    static boolean setErrorAndLog(HttpServletRequest request, String error) {
        String errorMsg = Prop.getDBProperty(error);
        request.setAttribute("errorMsg", errorMsg);
        logger.warn(errorMsg);
        return false;
    }

    static boolean valid_EmptyFields(HttpServletRequest request, String... args) {
        for (String s : args) {
            if (s == null || s.equals("")) {
                setErrorAndLog(request, "invalid.fields");
                return false;
            }
        }
        return true;
    }

    static boolean valid_Registration(Logins user, HttpServletRequest request) {
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

    static boolean valid_DishUpdate(HttpServletRequest request, String... args) {
        if (!valid_EmptyFields(request, args)) {
            return false;
        }
        // todo check name-ua, name_en
        Matcher m;
        m = Pattern.compile(PRICE_REGEX).matcher(args[2]);
        if (!m.find() || args[2].equals("0")) {
            return setErrorAndLog(request, "invalid.price");
        }
        return true;
    }

    static boolean valid_OrdersConfirm(HttpServletRequest request, String... args) {
        if (!valid_EmptyFields(request, args)) {
            return false;
        }

        Status status;
        long id;
        try {
            id = Long.parseLong(args[0]);
            status = Status.valueOf(args[1]);
        } catch (Exception e) {
            setErrorAndLog(request, "invalid.fields");
            return false;
        }

        if (status.equals(Status.DONE)
                || status.equals(Status.NEW)
                || id <= 0) {
            setErrorAndLog(request, "invalid.update.status");
            return false;
        }
        return true;
    }

    static boolean valid_ID(HttpServletRequest request, String args) {
        if (!valid_EmptyFields(request, args)) {
            return false;
        }

        long id;
        try {
            id = Long.parseLong(args);
        } catch (Exception e) {
            setErrorAndLog(request, "invalid.id");
            return false;
        }
        if (id <= 0) {
            setErrorAndLog(request, "invalid.positive.id");
            return false;
        }
        return true;
    }

}
