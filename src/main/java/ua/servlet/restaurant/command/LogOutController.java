package ua.servlet.restaurant.command;

import ua.servlet.restaurant.dao.entity.Logins;
import ua.servlet.restaurant.dao.entity.RoleType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Log out functional
 * Delete user from context loggedUsers list
 * Set into session guest user
 */
public class LogOutController implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        logger.info("Success logout user:" +
                ((Logins)session.getAttribute("principal")).getLogin());
        CommandUtility.deleteUserFromSession(session);
        CommandUtility.setUserIntoSession(request,
                Logins.builder().login("guest").role(RoleType.ROLE_GUEST).build());
        return "redirect:/index.jsp";
    }
}
