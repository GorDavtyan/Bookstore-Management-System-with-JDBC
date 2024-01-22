package com.picsart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Utility class for generating sales reports in the Bookstore Management System.
 */
public class SalesReports {

    /**
     * Generates a sales report for books, including sale ID, book title, customer name, and sale date.
     *
     * @param connection The database connection.
     */
    public static void generateBookSalesReport(Connection connection) {
        String selectQuery = "SELECT Sales.SaleID AS sale_id, Books.Title AS book_title, " +
                "Customers.Name AS customer_name, " +
                "Sales.DateOfSale AS sale_date " +
                "FROM Sales " +
                "JOIN Books ON Sales.BookID = Books.BookID " +
                "JOIN Customers ON Sales.CustomerID = Customers.CustomerID";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int saleId = resultSet.getInt("sale_id");
                String bookTitle = resultSet.getString("book_title");
                String customerName = resultSet.getString("customer_name");
                String saleDate = resultSet.getString("sale_date");

                System.out.println("Sale ID: " + saleId +
                        ", Book Title: " + bookTitle +
                        ", Customer Name: " + customerName +
                        ", Sale Date: " + saleDate);
            }

        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            System.out.println(e.getSQLState());
            System.out.println(e.getMessage());
        }
    }


    /**
     * Generates a revenue report by genre, including genre and total revenue.
     *
     * @param connection The database connection.
     */
    public static void generateRevenueReportByGenre(Connection connection) {
        String selectQuery = "SELECT Books.Genre, SUM(Sales.TotalPrice) AS total_revenue " +
                "FROM Books " +
                "JOIN Sales ON Books.BookID = Sales.BookID " +
                "GROUP BY Books.Genre";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String genre = resultSet.getString("Books.Genre");
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
