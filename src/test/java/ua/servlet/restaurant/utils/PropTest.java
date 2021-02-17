package ua.servlet.restaurant.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.util.Properties;

public class PropTest {
    private static final Logger logger = LogManager.getLogger(Prop.class);

    private static final String PATH   = "src/test/resources/";
    private static final String APP    = PATH + "test.properties";

    private static String getter(String propName) {
        try (FileInputStream fis = new FileInputStream(APP)) {
            Properties p = new Properties();
            p.load(fis);
            return (String)p.get(propName);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public static String getProperty(String propName) {
        return getter(propName);
    }

}
