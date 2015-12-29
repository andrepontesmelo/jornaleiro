package ngimj.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLAccess {
    private static MySQLAccess ourInstance = new MySQLAccess();

    public static MySQLAccess getInstance() {
        return ourInstance;
    }

    private MySQLAccess() {
    }

    private static final String URL = "jdbc:mysql://localhost:3306/imjoias";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "mircvinhad";

    public Connection getConnection() {
        Connection connection = null;

        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");

            } catch (ClassNotFoundException e) {
                return null;
            }

            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}