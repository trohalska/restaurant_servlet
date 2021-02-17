package ua.servlet.restaurant.servlet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.servlet.restaurant.utils.PropTest;

import static org.mockito.Mockito.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;

public class ServletTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    RequestDispatcher dispatcher;
    @Mock
    HttpSession session;
    @Mock
    ServletContext servletContext;
    @Mock
    ServletConfig servletConfig;

    Servlet servlet;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        servlet = new Servlet();
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        servlet.init(servletConfig);
    }

    @Test
    public void testServletLogin() throws IOException, ServletException {
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getServletContext()).thenReturn(servletContext);
        when(request.getSession().getServletContext().getAttribute("loggedUsers")).thenReturn(new HashSet<String>());

        when(request.getRequestURI()).thenReturn("restaurant/app/login");
        when(request.getParameter("username")).thenReturn(PropTest.getProperty("test.user"));
        when(request.getParameter("password")).thenReturn(PropTest.getProperty("test.pass"));

        when(request.getRequestDispatcher("/login.jsp")).thenReturn(dispatcher);

        servlet.init(servletConfig);
        servlet.doPost(request, response);

        verify(response, times(1)).sendRedirect("app/customer/");
        verify(request, times(5)).getSession();
    }

    @Test
    public void testServletMain() throws IOException, ServletException {
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getServletContext()).thenReturn(servletContext);
        when(request.getSession().getServletContext().getAttribute("loggedUsers")).thenReturn(new HashSet<String>());

        when(request.getRequestURI()).thenReturn("restaurant/app/menu");
        when(request.getParameter("page")).thenReturn("1");
        when(request.getParameter("sort")).thenReturn("id");
        when(request.getParameter("direct")).thenReturn("ASC");
        when(request.getParameter("category")).thenReturn("0");
        when(request.getSession().getAttribute("lang")).thenReturn("en");

        when(request.getRequestDispatcher("/WEB-INF/menu.jsp")).thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher("/WEB-INF/menu.jsp");
        verify(request, times(5)).getSession();
        verify(dispatcher).forward(request, response);
    }

}



//public class ServletTest {
//    private final static String path = "restaurant/app/login";
//
//    @Mock
//    HttpServletRequest request;
//    @Mock
//    HttpServletResponse response;
//    @Mock
//    HttpSession session;
//    @Mock
//    RequestDispatcher rd;
//
//    @Before
//    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void test() throws IOException, ServletException {
////        final Servlet servlet = new Servlet();
////        when(request.getParameter("user")).thenReturn("abhinav");
////        when(request.getParameter("password")).thenReturn("passw0rd");
////        when(request.getParameter("rememberMe")).thenReturn("Y");
//
//        when(request.getParameter("username")).thenReturn("ww");
//        when(request.getParameter("password")).thenReturn("1");
//
//        when(request.getSession()).thenReturn(session);
//        when(request.getRequestDispatcher("/login")).thenReturn(rd);
//
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//
//        when(response.getWriter()).thenReturn(pw);
//
//
//        new Servlet().doGet(request, response);
//
//        verify(request, times(1)).getRequestDispatcher(path);
//        verify(request, never()).getSession();
//        verify(rd).forward(request, response);
//
//        // Verify the session attribute value
////        verify(session).setAttribute("user", "abhinav");
////        verify(rd).forward(request, response);
//        String result = sw.getBuffer().toString().trim();
//        System.out.println("Result: " + result);
////        assertEquals("Login successfull...", result);
////        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);
//    }
//
//}
