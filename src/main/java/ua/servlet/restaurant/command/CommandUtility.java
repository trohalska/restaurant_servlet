package ua.servlet.restaurant.command;

import ua.servlet.restaurant.dao.entity.Logins;
import ua.servlet.restaurant.dao.entity.RoleType;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

/**
 * context has all loggedUsers, current userName
 * session has current user role
 */
public class CommandUtility {
    public static void setUserRole(HttpServletRequest request,
                            RoleType role, Logins user) {
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
        context.setAttribute("principal", user);
        session.setAttribute("userName", user.getLogin());
        session.setAttribute("role", role);
    }

    public static void deleteUserFromContext(ServletContext context) {
        Logins principal = (Logins) context.getAttribute("principal");
        HashSet<String> loggedUsers = (HashSet<String>) context.getAttribute("loggedUsers");

        if (principal != null) {
            loggedUsers.remove(principal.getLogin());
        }
        context.setAttribute("loggedUsers", loggedUsers);
    }


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
     * Gets principal for converting tables to dao
     * @param request for context
     * @return String "en" or "ua"
     */
    public static Logins getPrincipal(HttpServletRequest request) {
        return (Logins)request.getServletContext().getAttribute("principal");
    }

    /**
     * Gets language for converting tables to dao
     * @param request for context
     * @return String "en" or "ua"
     */
    public static String getLocale(HttpServletRequest request) {
        return (String)request.getServletContext().getAttribute("lang");
    }


}
