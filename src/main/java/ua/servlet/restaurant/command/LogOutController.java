package ua.servlet.restaurant.command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class LogOutController implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        // ToDo delete current user (context & session)
        ServletContext context = request.getSession().getServletContext();
        CommandUtility.deleteUserFromContext(context);

        CommandUtility.setUserRole(request, null, null);
        return "redirect:/index.jsp";
    }
}
