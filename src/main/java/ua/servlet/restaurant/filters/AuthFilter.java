package ua.servlet.restaurant.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.servlet.restaurant.command.CommandUtility;
import ua.servlet.restaurant.dao.entity.Logins;
import ua.servlet.restaurant.dao.entity.RoleType;
import ua.servlet.restaurant.utils.Prop;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
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

        if (session.getAttribute("principal") == null) {
            CommandUtility.setUserRole(request,
                    Logins.builder().login("guest").role(RoleType.ROLE_GUEST).build());
        }

        logger.info(session);
        logger.info(session.getAttribute("principal"));
        logger.info(servletRequest.getServletContext().getAttribute("loggedUsers"));

        String path = request.getRequestURI();
        RoleType role = ((Logins)session.getAttribute("principal")).getRole();

        if (path.contains("customer")) {
            if (role.equals(RoleType.ROLE_GUEST)) {
                throw new ServletException(Prop.getDBProperty("error.access.denied"));
            }
        } else if (path.contains("manager")) {
            if (role.equals(RoleType.ROLE_GUEST)
                    || role.equals(RoleType.ROLE_CUSTOMER)) {
                throw new ServletException(Prop.getDBProperty("error.forbidden.page"));
            }
        }
        if ((path.contains("login")
                || (path.contains("registration")))
            && (role.equals(RoleType.ROLE_CUSTOMER)
                || role.equals(RoleType.ROLE_MANAGER))) {
            throw new ServletException(Prop.getDBProperty("error.for.logged.user"));
        }

        filterChain.doFilter(servletRequest,servletResponse);

    }

    @Override
    public void destroy() {
    }
}
