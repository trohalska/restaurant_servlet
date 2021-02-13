package ua.servlet.restaurant.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Utils {

    public static void printRS(ResultSet resultSet) throws SQLException {
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print("\n");
                String columnValue = resultSet.getString(i);
                System.out.print(i + " _ " + columnValue + " " + rsmd.getColumnName(i));
            }
            System.out.println("");
        }
    }
}
