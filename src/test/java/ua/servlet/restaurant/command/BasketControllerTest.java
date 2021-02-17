package ua.servlet.restaurant.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import ua.servlet.restaurant.dao.entity.Logins;
import ua.servlet.restaurant.utils.Prop;
import ua.servlet.restaurant.utils.PropTest;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BasketControllerTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpSession session;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("lang")).thenReturn("en");
    }

    @Test
    public void success() throws ServletException, IOException {
        when(request.getSession().getAttribute("principal")).
                thenReturn(Logins.builder()
                        .id(Long.parseLong(PropTest.getProperty("test.id")))
                        .login(PropTest.getProperty("test.user"))
                        .build());

        String result = new BasketController().execute(request);

        assertEquals("/WEB-INF/basket.jsp", result);
        assertNull(request.getAttribute("errorMsg"));
    }

    @Test
    public void error() throws ServletException, IOException {
        when(request.getSession().getAttribute("principal")).
                thenReturn(Logins.builder()
                        .id(0L)
                        .login(PropTest.getProperty("errorUser"))
                        .build());

        String result = new BasketController().execute(request);

        assertEquals("/WEB-INF/basket.jsp", result);
        verify(request, times(1))
                .setAttribute("errorMsg", Prop.getDBProperty("select.all.baskets.empty"));
    }

}