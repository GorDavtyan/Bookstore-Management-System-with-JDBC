package com.picsart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for managing database connections in the Bookstore Management System.
 */
public class DatabaseConnection {

    /**
     * Database connection URL.
     */
    private static final String ADDRESS = "jdbc:postgresql://localhost:32769/bookstore_db";

    /**
     * Database username.
     */
    private static final String USER_NAME = "postgres";

    /**
     * Database password.
     */
    public static final String PASSWORD = "postgres";

    /**
     * Establishes a connection to the database.
     *
     * @return The established database connection.
     */
    public static Connection connection() {

        try {
            return DriverManager.getConnection(ADDRESS, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            System.out.println(e.getSQLState());
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Closes the given database connection.
     *
     * @param connection The database connection to close.
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getErrorCode());
                System.out.println(e.getSQLState());
                System.out.println(e.getMessage());
            }
        }
    }
}
