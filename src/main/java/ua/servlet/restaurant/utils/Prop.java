package ua.servlet.restaurant.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.util.Properties;

public class Prop {
    private static final Logger logger = LogManager.getLogger(Prop.class);

    private static final String PATH        = "src/main/resources/";
    private static final String PROPERTIES  = PATH + "application.properties";
    private static final String DB          = PATH + "db.properties";

    private static String getter(String propName, String source) {
        try (FileInputStream fis = new FileInputStream(source)) {
            Properties p = new Properties();
            p.load(fis);
            return (String)p.get(propName);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public static String getProperty(String propName) {
        return getter(propName, PROPERTIES);
    }

    public static String getDBProperty(String propName) {
        return getter(propName, DB);
    }
}
