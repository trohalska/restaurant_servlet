package ua.servlet.restaurant.filters;

import ua.servlet.restaurant.model.Logins;
import ua.servlet.restaurant.model.RoleType;

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
 */
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();
        ServletContext context = servletRequest.getServletContext();

        System.out.println(session);
        System.out.println(session.getAttribute("role"));
        System.out.println(context.getAttribute("loggedUsers"));

        String path = request.getRequestURI();

        // guest access
        if (path.contains("customer") || path.contains("manager")) {
            if (session.getAttribute("role") == null) {
                servletResponse.getWriter().append("AccessDenied! You need to authorize!");
                return;
            }
        }

        // customer access
        if (path.contains("manager") &&
                session.getAttribute("role").equals(RoleType.ROLE_CUSTOMER)) {
            servletResponse.getWriter().append("AccessDenied! Forbidden page!");
            return;
        }

        filterChain.doFilter(servletRequest,servletResponse);

    }

    @Override
    public void destroy() {
    }
}
