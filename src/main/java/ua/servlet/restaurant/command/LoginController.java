package ua.servlet.restaurant.command;

import org.mindrot.jbcrypt.BCrypt;
import ua.servlet.restaurant.model.Logins;
import ua.servlet.restaurant.model.RoleType;
import ua.servlet.restaurant.service.LoginsService;

import javax.servlet.http.HttpServletRequest;

public class LoginController implements Command {
    private final LoginsService loginsService;
    public LoginController() {
        this.loginsService = new LoginsService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println(login + " " + password);
        logger.info(login + " " + password);

        if ( login == null || login.equals("") || password == null || password.equals("") ){
            return "/login.jsp";
        }

        Logins user = loginsService.findByLogin(login);

        if (BCrypt.checkpw(password, user.getPassword())) {
            if (CommandUtility.checkUserIsLogged(request, login)) {
                return "/WEB-INF/error.jsp";
            }
            logger.info("User "+ login + " logged successfully.");

            if (user.getRole().equals(RoleType.ROLE_MANAGER)){
                CommandUtility.setUserRole(request, RoleType.ROLE_MANAGER, login);
                return "redirect:/app/manager/";
            } else if(user.getRole().equals(RoleType.ROLE_CUSTOMER)) {
                CommandUtility.setUserRole(request, RoleType.ROLE_CUSTOMER, login);
                return "redirect:/app/customer/";
            }
        }
        // TODO set error msg
        logger.info("Invalid attempt of login user:" + login);
        return "/login.jsp";
    }

}
