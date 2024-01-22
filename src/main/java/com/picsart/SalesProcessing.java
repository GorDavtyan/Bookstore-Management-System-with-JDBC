package com.picsart;

import java.sql.*;

/**
 * Utility class for processing sales and calculating total revenue in the Bookstore Management System.
 */
public class SalesProcessing {

    /**
     * Processes a new sale transaction, updating stock quantity and recording the sale in the database.
     *
     * @param connection   The database connection.
     * @param customerId   The ID of the customer making the purchase.
     * @param bookId       The ID of the book being purchased.
     * @param dateOfSale   The date of the sale.
     * @param quantity     The quantity of books being purchased.
     */
    public static void processNewSale(Connection connection, int customerId, int bookId, Date dateOfSale, int quantity) {
        try {
            connection.setAutoCommit(false);

            if (isStockAvailable(connection, bookId, quantity)) {

                double totalPrice = calculateTotalPrice(connection, bookId, quantity);

                updateStockQuantity(connection, bookId, quantity);

                insertSaleRecord(connection, bookId, customerId, dateOfSale, quantity, totalPrice);

                connection.commit();

                System.out.println("Sale processed successfully. Total Price: $" + totalPrice);
            } else {
                System.out.println("Not enough stock available for the requested quantity.");
            }

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                System.out.println(e.getMessage());
            }
            System.out.println(e.getSQLState());
            System.out.println(e.getMessage());
            System.out.println(e.getErrorCode());
        }
    }


    /**
     * Checks if there is sufficient stock available for a given book and quantity.
     *
     * @param connection The database connection.
     * @param bookId     The ID of the book.
     * @param quantity   The requested quantity.
     * @return True if there is enough stock, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    private static boolean isStockAvailable(Connection connection, int bookId, int quantity) throws SQLException {
        String selectQuery = "SELECT Books.QuantityInStock FROM Books WHERE BookID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, bookId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int currentStock = resultSet.getInt("Books.QuantityInStock");
                    return currentStock >= quantity;
                }
            }
        }
        return false;
    }

    /**
     * Calculates the total price for a given book and quantity.
     *
     * @param connection The database connection.
     * @param bookId     The ID of the book.
     * @param quantity   The quantity of books.
     * @return The total price for the purchase.
     * @throws SQLException If a database access error occurs.
     */
    private static double calculateTotalPrice(Connection connection, int bookId, int quantity) throws SQLException {
        String selectQuery = "SELECT Books.Price FROM Books WHERE BookID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, bookId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    double unitPrice = resultSet.getDouble("Books.Price");
                    return unitPrice * quantity;
                }
            }
        }
        return 0;
    }

    /**
     * Updates the stock quantity of a given book after a successful sale.
     *
     * @param connection The database connection.
     * @param bookId     The ID of the book.
     * @param quantity   The quantity of books sold.
     * @throws SQLException If a database access error occurs.
     */
    private static void updateStockQuantity(Connection connection, int bookId, int quantity) throws SQLException {
        String updateQuery = "UPDATE Books SET Books.QuantityInStock = Books.QuantityInStock - ? WHERE BookID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, bookId);

            preparedStatement.executeUpdate();
        }
    }

    /**
     * Inserts a new sale record into the database.
     *
     * @param connection   The database connection.
     * @param bookId       The ID of the book sold.
     * @param customerId   The ID of the customer making the purchase.
     * @param dateOfSale   The date of the sale.
     * @param quantitySold The quantity of books sold.
     * @param totalPrice   The total price of the sale.
     * @throws SQLException If a database access error occurs.
     */
    private static void insertSaleRecord(Connection connection, int bookId, int customerId, Date dateOfSale, int quantitySold, double totalPrice) throws SQLException {
        String insertQuery = "INSERT INTO Sales (BookID, CustomerID, DateOfSale, QuantitySold, TotalPrice) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setInt(1, bookId);
            preparedStatement.setInt(2, customerId);
            preparedStatement.setDate(3, dateOfSale);
            preparedStatement.setInt(4, quantitySold);
            preparedStatement.setDouble(5, totalPrice);

            preparedStatement.executeUpdate();
        }
    }


    /**
     * Calculates and prints the total revenue by genre.
     *
     * @param connection The database connection.
     */
    public static void calculateTotalRevenueByGenre(Connection connection) {
        String selectQuery = "SELECT Books.Genre, SUM(Sales.TotalPrice) AS total_revenue FROM Sales " +
                "JOIN Books ON Sales.BookID = Books.BookID " +
                "GROUP BY Books.Genre";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String genre = resultSet.getString("Genre");
                double totalRevenue = resultSet.getDouble("total_revenue");
                System.out.println("Genre: " + genre + ", Total Revenue: $" + totalRevenue);
            }

        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            System.out.println(e.getSQLState());
            System.out.println(e.getMessage());
        }

    }
}
