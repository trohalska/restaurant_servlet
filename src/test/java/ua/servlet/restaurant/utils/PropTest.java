package ua.servlet.restaurant.utils;

import org.junit.Test;
import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.Assert.*;

public class PropTest {

    @Test
    public void success() throws ServletException, IOException {
        String result = Prop.getDBProperty("invalid.fields");
        assertEquals(Prop.getDBProperty("invalid.fields"), result);

        String result2 = Prop.getProperty("connection.pass");
        assertEquals(Prop.getProperty("connection.pass"), result2);
    }

    @Test
    public void error() throws ServletException, IOException {
        String result = Prop.getDBProperty("invalid.fields.test");
        assertNull(result);
    }

}