package ua.servlet.restaurant.dao.impl;

import org.apache.commons.dbcp.BasicDataSource;
import ua.servlet.restaurant.utils.Prop;

import javax.sql.DataSource;

public class ConnectionPoolHolder {
    private static volatile DataSource dataSource;
    public static DataSource getDataSource(){

        if (dataSource == null){
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    BasicDataSource ds = new BasicDataSource();
//                    ds.setUrl("jdbc:mysql://localhost:3306/mystudentdb");
//                    ds.setUsername("root");
//                    ds.setPassword("root");
                    ds.setUrl(Prop.getProperty("connection.url"));
                    ds.setUsername(Prop.getProperty("connection.user"));
                    ds.setPassword(Prop.getProperty("connection.pass"));
                    ds.setMinIdle(5);
                    ds.setMaxIdle(10);
                    ds.setMaxOpenPreparedStatements(100);
                    dataSource = ds;
                }
            }
        }
        return dataSource;
    }

}
