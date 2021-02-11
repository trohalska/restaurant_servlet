package ua.servlet.restaurant.command;

import ua.servlet.restaurant.dao.entity.RoleType;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class LogOutController implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        ServletContext context = request.getSession().getServletContext();
        logger.info("Success logout user:" + context.getAttribute("userName"));
        CommandUtility.deleteUserFromContext(context);
        CommandUtility.setUserRole(request, RoleType.ROLE_GUEST, "guest");
        return "redirect:/index.jsp";
    }
}
