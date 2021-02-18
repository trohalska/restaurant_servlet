package ua.servlet.restaurant.command;

import org.mindrot.jbcrypt.BCrypt;
import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.entity.Logins;
import ua.servlet.restaurant.dao.entity.RoleType;
import ua.servlet.restaurant.service.LoginsService;
import ua.servlet.restaurant.utils.Prop;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * Log in functional
 * Get username and password as input, make validation.
 * Authentication: by username and password, and check if user already logged in
 * Authorization by role
 *
 * Set errorMsg if:
 * - login does not match (cannot find in DB)
 * - password does not match (BCrypt encoding)
 */
public class LoginController implements Command {
    private final LoginsService loginsService;
    public LoginController() {
        this.loginsService = new LoginsService();
    }

    /**
     * Validation and Authentication by login
     * @param request request
     * @return page
     * @throws ServletException if role not customer or manager
     */
    @Override
    public String execute(HttpServletRequest request) throws ServletException {
        String login = request.getParameter("username");
        String password = request.getParameter("password");
        logger.info(login + " " + password);

        if (login == null || login.equals("") || password == null || password.equals("")) {
            return "/login.jsp";
        }
        Logins user;
        try {
            user = loginsService.findByLogin(login);
        } catch (DBException e) {
            logger.warn(e.getMessage());
            request.setAttribute("errorMsg", e.getMessage());
            return "/login.jsp";
        }
        logger.info(user);
        return authenticatePassword(request, user, password);

    }

    /**
     * Authentication by password
     * @param request request
     * @param user user, that was found in DB by login
     * @param password password of user that wants to log in
     * @return page
     * @throws ServletException if role not customer or manager
     */
    private String authenticatePassword(HttpServletRequest request, Logins user,
                                        String password) throws ServletException {

        if (!BCrypt.checkpw(password, user.getPassword())) {
            String errorMsg = Prop.getDBProperty("select.login.byLogin.dbe.pass") + user.getLogin();
            logger.warn(errorMsg);
            request.setAttribute("errorMsg", errorMsg);
            return "/login.jsp";
        }

        if (CommandUtility.checkUserIsLogged(request, user.getLogin())) {
            String errorMsg = Prop.getDBProperty("invalid.user");
            logger.warn(errorMsg);
            request.setAttribute("errorMsg", errorMsg);
            return "/login.jsp";
        }
        logger.info(Prop.getDBProperty("select.login.byLogin.success") + user.getLogin());
        return authorizationByRole(request, user);
    }

    /**
     * Redirect on customer address or manager address
     * @param request request
     * @param user user for getting role
     * @return redirect
     * @throws ServletException if role not customer or manager
     */
    private String authorizationByRole(HttpServletRequest request, Logins user) throws ServletException {
        CommandUtility.setUserIntoSession(request, user);
        if (user.getRole().equals(RoleType.ROLE_MANAGER)){
            return "redirect:/manager/";
        } else if (user.getRole().equals(RoleType.ROLE_CUSTOMER)) {
            return "redirect:/customer/";
        }
        throw new ServletException(Prop.getDBProperty("error.unknown.role"));
    }

}
