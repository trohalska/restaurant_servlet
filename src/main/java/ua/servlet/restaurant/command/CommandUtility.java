package ua.servlet.restaurant.command;

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
    static void setUserRole(HttpServletRequest request,
                            RoleType role, String name) {
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
        context.setAttribute("userName", name);
        session.setAttribute("userName", name);
        session.setAttribute("role", role);
    }

    public static void deleteUserFromContext(ServletContext context) {
        String userName = (String) context.getAttribute("userName");
        HashSet<String> loggedUsers = (HashSet<String>) context.getAttribute("loggedUsers");

        loggedUsers.remove(userName);
        System.out.println(userName);
//        session.setAttribute("loggedUsers", loggedUsers);
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


}
