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

        if ( login == null || login.equals("") || password == null || password.equals("") ){
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

        return checkPassword(request, user, login, password);

    }

    private String checkPassword(HttpServletRequest request, Logins user,
                                 String login, String password) throws ServletException {

        if (BCrypt.checkpw(password, user.getPassword())) {
            if (CommandUtility.checkUserIsLogged(request, login)) {
                return "/WEB-INF/error.jsp";
            }
            logger.info(Prop.getDBProperty("select.login.byLogin.success") + login);
            return redirectByRoles(request, login, user);
        }

        String errorMsg = Prop.getDBProperty("select.login.byLogin.dbe.pass") + login;
        logger.warn(errorMsg);
        request.setAttribute("errorMsg", errorMsg);
        return "/login.jsp";
    }

    private String redirectByRoles(HttpServletRequest request, String login, Logins user) throws ServletException {
        if (user.getRole().equals(RoleType.ROLE_MANAGER)){
            CommandUtility.setUserRole(request, RoleType.ROLE_MANAGER, login);
            return "redirect:/app/manager/";
        } else if(user.getRole().equals(RoleType.ROLE_CUSTOMER)) {
            CommandUtility.setUserRole(request, RoleType.ROLE_CUSTOMER, login);
            return "redirect:/app/customer/";
        }
        throw new ServletException(Prop.getDBProperty("error.unknown.role"));
    }

}
