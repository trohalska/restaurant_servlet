package ua.servlet.restaurant.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class PropBundler {
    public static String getProps(String propName) {
        try (FileInputStream fis = new FileInputStream("app.properties")) {
            Properties p = new Properties();
            p.load(fis);
            return (String)p.get(propName);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}
