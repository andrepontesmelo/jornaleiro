package jornaleiro.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PgSQLAccess {
    private static PgSQLAccess ourInstance = new PgSQLAccess();

    public static PgSQLAccess getInstance() {
        return ourInstance;
    }

    private PgSQLAccess() {
    }

    private static final String URL = "jdbc:postgresql://localhost:5432/jornaleiro";
    private static final String USERNAME = "jornaleiro";
    private static final String PASSWORD = "";

    public Connection getConnection() {
        Connection connection = null;

        try {
            try {
                Class.forName("org.postgresql.Driver");

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