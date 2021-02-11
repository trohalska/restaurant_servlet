package ua.servlet.restaurant.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.servlet.restaurant.command.Command;
import ua.servlet.restaurant.command.CommandUtility;
import ua.servlet.restaurant.dao.entity.RoleType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Access:
 *      - guest -> access to basic pages
 *      - customer -> access to basic and customer pages
 *      - manager -> access everywhere
 *      - customer and manager -> cant access to login and registration (till logout)
 */
public class AuthFilter implements Filter {
    Logger logger = LogManager.getLogger(AuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        ServletContext context = servletRequest.getServletContext();

        if (session.getAttribute("role") == null) {
            CommandUtility.setUserRole(request, RoleType.ROLE_GUEST, "guest");
        }

        logger.info(session);
        logger.info(session.getAttribute("role"));
        logger.info(context.getAttribute("loggedUsers"));

        String path = request.getRequestURI();

        Object role = session.getAttribute("role");

        if (path.contains("customer")) {
            if (role.equals(RoleType.ROLE_GUEST)) {

                servletResponse.getWriter().append("AccessDenied! You need to authorize!");
                return;
            }
        } else if (path.contains("manager")) {
            if (role.equals(RoleType.ROLE_GUEST)
                    || role.equals(RoleType.ROLE_CUSTOMER)) {

                servletResponse.getWriter().append("AccessDenied! Forbidden page!");
                return;
            }
        }
        if ((path.contains("login")
                || (path.contains("registration")))
            && (role.equals(RoleType.ROLE_CUSTOMER)
                || role.equals(RoleType.ROLE_MANAGER))) {

            servletResponse.getWriter().append("AccessDenied! You need to logout first!");
            return;
        }

        filterChain.doFilter(servletRequest,servletResponse);

    }

    @Override
    public void destroy() {
    }
}
