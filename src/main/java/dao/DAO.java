package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Edited by:
 * - Roger
 */
public abstract class Dao {

    private String user = "admin";
    private String password = "12345";

    /**
     * getConnection() returns een connection met de database.
     */
    protected Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(
                    //TODO: OBTAIN IP ADRES FROM CONFIG
                    "jdbc:mysql://localhost/lionsclub?"
                            + "user=" + user
                            + "&password=" + password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
