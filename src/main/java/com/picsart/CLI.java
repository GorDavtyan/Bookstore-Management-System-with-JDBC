package com.picsart;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/**
 * Command Line Interface (CLI) class for user interactions.
 */
public class CLI {

    /**
     * Displays the main menu of the Bookstore Management System.
     */
    public void displayMenu() {
        System.out.println("Bookstore Management System Menu:");
        System.out.println("1. Book Management");
        System.out.println("2. Customer Management");
        System.out.println("3. Sales Processing");
        System.out.println("4. Sales Reports");
        System.out.println("5. Exit");
        System.out.print("Enter your choice (1-5): ");
    }

    /**
     * Gets the user's choice from the console.
     *
     * @param scanner The Scanner object for user input.
     * @return The user's choice as a String.
     */
    public String getChoice(Scanner scanner) {
        return scanner.nextLine();
    }

    /**
     * Handles user input for book management.
     *
     * @param scanner    The Scanner object for user input.
     * @param connection The database connection.
     */
    public void inputForBooksManagement(Scanner scanner, Connection connection) {
        displayForBooksManagement(scanner, connection);
    }

    private void updateBooks(Scanner scanner, Connection connection) {
        System.out.println("Please enter the ID of the book to update");
        int bookID = generateValidNumber(scanner);
        scanner.nextLine();
        System.out.println("Please enter the new title");
        String title = scanner.nextLine();
        System.out.println("Please enter the new author");
        String author = scanner.nextLine();
        System.out.println("Please enter the new genre");
        String genre = scanner.nextLine();
        System.out.println("Please enter the new price");
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid statement, please enter a number");
            scanner.next();
        }
        double newPrice = scanner.nextDouble();
        System.out.println("Please enter the new new quantity in stock");
        int newQuantityInStock = generateValidNumber(scanner);
        scanner.nextLine();


        BooksManagement.updateBookDetails(
                connection,
                bookID,
                title,
                author,
                genre,
                newPrice,
                newQuantityInStock);
    }

    private void searchBooks(Scanner scanner, Connection connection) {
        String searchBy;
        do {
            System.out.println("Enter 'genre' or 'author' to search books:");
            searchBy = scanner.nextLine();
        } while (!(searchBy.equalsIgnoreCase("genre") || searchBy.equalsIgnoreCase("author")));
        System.out.println("Enter the value to search for:");
        String searchValue = scanner.nextLine();

        BooksManagement.listBooksByGenreOrAuthor(connection, searchBy, searchValue);
    }

    private void displayForBooksManagement(Scanner scanner, Connection connection) {

        while (true) {
            System.out.println("Books Management System Menu.");
            System.out.println("1. Update book details.");
            System.out.println("2. Search books by genre or author.");
            System.out.println("3. Exit");
            System.out.println("Enter your choice 1, 2 or 3");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> updateBooks(scanner, connection);
                case "2" -> searchBooks(scanner, connection);
                case "3" -> System.out.println("Existing books management system");
                default -> System.out.println("Invalid choice. Please enter a valid option.");
            }

            if (choice.equals("3")) {
                break;
            }
        }
    }

    /**
     * Handles user input for customers management.
     *
     * @param scanner    The Scanner object for user input.
     * @param connection The database connection.
     */
    public void inputForCustomersManagement(Scanner scanner, Connection connection) {
        displayForCustomersManagement(scanner, connection);
    }

    private void displayForCustomersManagement(Scanner scanner, Connection connection) {
        while (true) {
            System.out.println("Customers Management System Menu.");
            System.out.println("1. Update customers information.");
            System.out.println("2. View a customer’s purchase history.");
            System.out.println("3. Exit");
            System.out.println("Enter your choice 1, 2 or 3");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> updateCustomersInformation(scanner, connection);
                case "2" -> customersPurchaseHistory(scanner, connection);
                case "3" -> System.out.println("Existing books management system");
                default -> System.out.println("Invalid choice. Please enter a valid option.");
            }

            if (choice.equals("3")) {
                break;
            }
        }
    }

    private void updateCustomersInformation(Scanner scanner, Connection connection) {
        System.out.println("Enter the ID of the customer to update:");
        int customerIdToUpdate = generateValidNumber(scanner);
        scanner.nextLine();

        System.out.println("Enter the new  name:");
        String newName = scanner.nextLine();

        System.out.println("Enter the new email:");
        String newEmail = scanner.nextLine();

        System.out.println("Enter the new phone:");
        String newPhone = scanner.nextLine();
        PhoneNumberValidation phoneNumberValidation = new PhoneNumberValidation();
        while (!phoneNumberValidation.phoneNumberValid(newPhone)) {
            newPhone = scanner.nextLine();
        }

        CustomerManagement.updateCustomerInfo(connection, customerIdToUpdate, newName, newEmail, newPhone);
    }

    private void customersPurchaseHistory(Scanner scanner, Connection connection) {
        System.out.println("Enter customer id");
        int customerIdToUpdate = generateValidNumber(scanner);

        CustomerManagement.viewCustomerPurchaseHistory(connection, customerIdToUpdate);
    }

    /**
     * Handles user input for sales processing.
     *
     * @param scanner    The Scanner object for user input.
     * @param connection The database connection.
     */
    public void inputForSalesProcessing(Scanner scanner, Connection connection) {
        displayForSalesProcessing(scanner, connection);
    }

    private void displayForSalesProcessing(Scanner scanner, Connection connection) {
        while (true) {
            System.out.println("Sales  Management System Menu.");
            System.out.println("1. Process for new sale.");
            System.out.println("2. Calculate total revenue by genre.");
            System.out.println("3. Exit");
            System.out.println("Enter your choice 1, 2 or 3");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> insertSalesProcessing(scanner, connection);
                case "2" -> SalesProcessing.calculateTotalRevenueByGenre(connection);
                case "3" -> System.out.println("Existing books management system");
                default -> System.out.println("Invalid choice. Please enter a valid option.");
            }

            if (choice.equals("3")) {
                break;
            }
        }
    }

    private void insertSalesProcessing(Scanner scanner, Connection connection) {
        System.out.println("Enter the customer ID:");
        int customerId = generateValidNumber(scanner);

        System.out.println("Enter the book ID:");
        int bookId = generateValidNumber(scanner);

        System.out.println("Enter the quantity:");
        int quantity = generateValidNumber(scanner);

        scanner.nextLine();
        System.out.println("Enter the date of sale:");
        String str = scanner.nextLine();
        ValidationDate validationDate = new ValidationDate();
        while (!validationDate.validDateFormat(str)) {
            System.out.println("Invalid statement, please enter the valid date of birth");
            str = scanner.nextLine();
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        java.sql.Date sqlDate = null;
        try {
            java.util.Date utilDate = dateFormat.parse(str);
            sqlDate = new java.sql.Date(utilDate.getTime());

        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        SalesProcessing.processNewSale(connection, customerId, bookId, sqlDate, quantity);
    }

    private void displayForReports(Scanner scanner, Connection connection) {
        while (true) {
            System.out.println("Sales Reports System Menu.");
            System.out.println("1. Books sales reports.");
            System.out.println("2. Revenue report by genre.");
            System.out.println("3. Exit");
            System.out.println("Enter your choice 1, 2 or 3");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> SalesReports.generateBookSalesReport(connection);
                case "2" -> SalesReports.generateRevenueReportByGenre(connection);
                case "3" -> System.out.println("Existing books management system");
                default -> System.out.println("Invalid choice. Please enter a valid option.");
            }

            if (choice.equals("3")) {
                break;
            }
        }
    }

    /**
     * Displays the sales reports menu and handles user input for generating reports.
     *
     * @param scanner    The Scanner object for user input.
     * @param connection The database connection.
     */
    public void generateReports(Scanner scanner, Connection connection) {
        displayForReports(scanner, connection);
    }

    /**
     * Generates a valid integer number from user input.
     *
     * @param scanner The Scanner object for user input.
     * @return A valid integer entered by the user.
     */
    private int generateValidNumber(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid statement, please enter a number");
            scanner.next();
        }
        return scanner.nextInt();
    }
}