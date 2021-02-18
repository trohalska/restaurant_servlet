package ua.servlet.restaurant.command;

import org.apache.logging.log4j.Logger;
import ua.servlet.restaurant.dao.entity.Logins;
import ua.servlet.restaurant.utils.Prop;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

/**
 * context has all logged users
 * session has current user as principal and user language
 */
public class CommandUtility {
    /**
     * Set principal in user session
     * @param request request
     * @param user user
     */
    public static void setUserIntoSession(HttpServletRequest request, Logins user) {
        request.getSession().setAttribute("principal", user);
    }

    /**
     * Delete user from context list of logged users
     * if user logs out or if sessions time goes off.
     * @param session user session
     */
    public static void deleteUserFromSession(HttpSession session) {
        Logins principal = (Logins)session.getAttribute("principal");
        HashSet<String> loggedUsers = (HashSet<String>) session.getServletContext().getAttribute("loggedUsers");
        if (principal != null) {
            loggedUsers.remove(principal.getLogin());
        }
        session.getServletContext().setAttribute("loggedUsers", loggedUsers);
    }

    /**
     * Checks if user isn't in context "loggedUsers" list when user tries to log in
     * @param request request
     * @param userName user login
     * @return true if user already logged, false if otherwise.
     */
    static boolean checkUserIsLogged(HttpServletRequest request, String userName){
        ServletContext context = request.getSession().getServletContext();

        HashSet<String> loggedUsers = (HashSet<String>) context.getAttribute("loggedUsers");
        if (loggedUsers.stream().anyMatch(userName::equals)) {
            return true;
        }
        loggedUsers.add(userName);
        context.setAttribute("loggedUsers", loggedUsers);
        return false;
    }

    /**
     * Gets principal from session.
     * @param request request
     * @return Logins user
     */
    public static Logins getPrincipal(HttpServletRequest request) {
        return (Logins)request.getSession().getAttribute("principal");
    }

    /**
     * Gets language from session for internationalization dao
     * @param request request
     * @return String "en" or "ua"
     */
    public static String getLocale(HttpServletRequest request) {
        return (String)request.getSession().getAttribute("lang");
    }


}
