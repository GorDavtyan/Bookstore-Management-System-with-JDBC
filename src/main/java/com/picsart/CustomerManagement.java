package com.picsart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for managing customer-related operations in the Bookstore Management System.
 */
public class CustomerManagement {

    /**
     * Updates the information of a customer in the database.
     *
     * @param connection  The database connection.
     * @param customerID  The ID of the customer to update.
     * @param name        The new name for the customer.
     * @param email       The new email for the customer.
     * @param phone       The new phone number for the customer.
     */
    public static void updateCustomerInfo(Connection connection, int customerID, String name, String email, String phone) {
        String updateQuery = "UPDATE Customers SET Name = ?, Email = ?, Phone = ? WHERE CustomerID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, phone);
            preparedStatement.setInt(4, customerID);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Customer information updated successfully.");
            } else {
                System.out.println("No customer found with the given ID.");
            }

        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            System.out.println(e.getSQLState());
            System.out.println(e.getMessage());
        }
    }

    /**
     * Views the purchase history of a customer from the database.
     *
     * @param connection The database connection.
     * @param customerID The ID of the customer to view purchase history for.
     */
    public static void viewCustomerPurchaseHistory(Connection connection, int customerID) {
        String selectQuery = "SELECT * FROM Sales WHERE CustomerID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, customerID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    System.out.println("Purchase history for customer with ID " + customerID + ":");
                    do {
                        System.out.println("Sale ID: " + resultSet.getInt("SaleID") +
                                ", Book ID: " + resultSet.getInt("BookID") +
                                ", Sale Date: " + resultSet.getDate("DateOfSale") +
                                ", QuantitySold: " + resultSet.getInt("QuantitySold"));
                    } while (resultSet.next());
                } else {
                    System.out.println("No purchase history found for the customer with ID " + customerID + ".");
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            System.out.println(e.getSQLState());
            System.out.println(e.getMessage());
        }
    }
}
