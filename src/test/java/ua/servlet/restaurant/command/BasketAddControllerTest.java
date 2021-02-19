package ua.servlet.restaurant.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.servlet.restaurant.dao.entity.Logins;
import ua.servlet.restaurant.utils.Prop;
import ua.servlet.restaurant.utils.PropUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class BasketAddControllerTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpSession session;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("lang")).thenReturn("en");
        when(request.getSession().getAttribute("principal")).
                thenReturn(Logins.builder()
                        .id(Long.parseLong(PropUtil.getProperty("test.id")))
                        .login(PropUtil.getProperty("test.user"))
                        .build());
    }

    @Test
    public void success() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn(PropUtil.getProperty("exist.dish.id"));

        String result = new BasketAddController().execute(request);

        assertEquals("redirect:/customer/basket", result);
        assertNull(request.getAttribute("errorMsg"));
    }

    @Test
    public void errorInvalidDishId() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn(PropUtil.getProperty("wrong.input.id"));

        String result = new BasketAddController().execute(request);

        assertEquals("/WEB-INF/basket.jsp", result);
        verify(request, times(1))
                .setAttribute("errorMsg", Prop.getDBProperty("invalid.id"));
    }

    @Test
    public void errorDishId() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn(PropUtil.getProperty("unreal.dish.id"));

        String result = new BasketAddController().execute(request);

        assertEquals("/WEB-INF/basket.jsp", result);
        verify(request, times(1))
                .setAttribute("errorMsg",
                        Prop.getDBProperty("create.basket.dbe") +
                                PropUtil.getProperty("unreal.dish.id"));
    }

}