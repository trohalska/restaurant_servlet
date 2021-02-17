package ua.servlet.restaurant.command;

import org.mindrot.jbcrypt.BCrypt;
import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.entity.Logins;
import ua.servlet.restaurant.dao.entity.RoleType;
import ua.servlet.restaurant.service.LoginsService;
import ua.servlet.restaurant.utils.Prop;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class LoginController implements Command {
    private final LoginsService loginsService;
    public LoginController() {
        this.loginsService = new LoginsService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServletException {
        String login = request.getParameter("username");
        String password = request.getParameter("password");
        logger.info(login + " " + password);

        if (login == null || login.equals("") || password == null || password.equals("")) {
            return "/login.jsp";
        }
//        if (!Validator.valid_EmptyFields(request, login, password)) {
//            return "/login.jsp";
//        }
        Logins user;
        try {
            user = loginsService.findByLogin(login);
        } catch (DBException e) {
            logger.warn(e.getMessage());
            request.setAttribute("errorMsg", e.getMessage());
            return "/login.jsp";
        }
        logger.info(user);
        return checkPassword(request, user, password);

    }

    private String checkPassword(HttpServletRequest request, Logins user,
                                 String password) throws ServletException {

        if (BCrypt.checkpw(password, user.getPassword())) {
            if (CommandUtility.checkUserIsLogged(request, user.getLogin())) {
                String errorMsg = Prop.getDBProperty("invalid.user");
                logger.warn(errorMsg);
                request.setAttribute("errorMsg", errorMsg);
                return "/login.jsp";
            }
            logger.info(Prop.getDBProperty(
                    "select.login.byLogin.success") + user.getLogin());
            return redirectByRoles(request, user);
        }

        String errorMsg = Prop.getDBProperty(
                "select.login.byLogin.dbe.pass") + user.getLogin();
        logger.warn(errorMsg);
        request.setAttribute("errorMsg", errorMsg);
        return "/login.jsp";
    }

    private String redirectByRoles(HttpServletRequest request, Logins user) throws ServletException {
        CommandUtility.setUserRole(request, user);
        if (user.getRole().equals(RoleType.ROLE_MANAGER)){
            return "redirect:/manager/";
        } else if(user.getRole().equals(RoleType.ROLE_CUSTOMER)) {
            return "redirect:/customer/";
        }
        throw new ServletException(Prop.getDBProperty("error.unknown.role"));
    }

}
