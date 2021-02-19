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

public class BasketDeleteAllControllerTest {
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
        String result = new BasketDeleteAllController().execute(request);
        assertEquals("redirect:/customer/basket", result);
        assertNull(request.getAttribute("errorMsg"));
    }

}