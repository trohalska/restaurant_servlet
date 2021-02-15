package ua.servlet.restaurant.utils;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import java.io.*;
import java.time.LocalDateTime;

public class ParseLocalDateTimeTag extends SimpleTagSupport {
    private LocalDateTime value;

    public void setValue(LocalDateTime value) {
        this.value = value;
    }

    public void doTag() throws JspException, IOException {
        if (value != null) {
            JspWriter out = getJspContext().getOut();
            out.println(parse(value));
        }
    }

    private String parse(LocalDateTime value) {
        String[] date = value.toString().replace("T", "  ").split("\\.");
        return date[0];
    }
}
