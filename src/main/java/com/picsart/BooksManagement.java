package com.picsart;

import java.sql.*;

/**
 * Class for managing book-related operations in the Bookstore Management System.
 */
public class BooksManagement {

    /**
     * Updates the details of a book in the database.
     *
     * @param connection         The database connection.
     * @param bookId             The ID of the book to update.
     * @param newTitle           The new title for the book.
     * @param newAuthor          The new author for the book.
     * @param newGenre           The new genre for the book.
     * @param newPrice           The new price for the book.
     * @param newQuantityInStock The new quantity in stock for the book.
     */
    public static void updateBookDetails(
            Connection connection,
            int bookId,
            String newTitle,
            String newAuthor,
            String newGenre,
            double newPrice,
            int newQuantityInStock
           ) {


        String updateQuery = "UPDATE Books SET Title = ?, Author = ?, Genre = ?, Price = ?, QuantityInStock = ? WHERE BookID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, newTitle);
            preparedStatement.setString(2, newAuthor);
            preparedStatement.setString(3, newGenre);
            preparedStatement.setDouble(4, newPrice);
            preparedStatement.setInt(5, newQuantityInStock);
            preparedStatement.setInt(6, bookId);


            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Book details updated successfully.");
            } else {
                System.out.println("No book found with the given ID.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Lists books based on a given genre or author from the database.
     *
     * @param connection The database connection.
     * @param searchBy   The field to search by (genre or author).
     * @param searchValue The value to search for in the specified field.
     */
    public static void listBooksByGenreOrAuthor(Connection connection, String searchBy, String searchValue) {

        String selectQuery = "SELECT * FROM Books WHERE " + searchBy + " = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, searchValue);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("Books found:");
                    do {
                        System.out.println("ID: " + resultSet.getInt("BookID") +
                                ", Title: " + resultSet.getString("Title") +
                                ", Author: " + resultSet.getString("Author") +
                                ", Genre: " + resultSet.getString("Genre") +
                                ", Price: " + resultSet.getDouble("Price") +
                                ", QuantityInStock: " + resultSet.getInt("QuantityInStock"));
                    } while (resultSet.next());
                } else {
                    System.out.println("No books found with the given " + searchBy + ".");
                }
            }
        }
        catch (SQLException e) {
            System.out.println(e.getErrorCode());
            System.out.println(e.getSQLState());
            System.out.println(e.getMessage());
        }

    }
}