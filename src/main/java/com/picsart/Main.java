package com.picsart;

import java.sql.Connection;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CLI cli = new CLI();
        Connection connection = DatabaseConnection.connection();

        if (connection != null) {
            System.out.println("Connected to the database!");

            while (true) {
                cli.displayMenu();
                String choice = cli.getChoice(scanner);
                switch (choice) {
                    case "1" -> cli.inputForBooksManagement(scanner, connection);
                    case "2" -> cli.inputForCustomersManagement(scanner, connection);
                    case "3" -> cli.inputForSalesProcessing(scanner, connection);
                    case "4" -> cli.generateReports(scanner, connection);
                    case "5" -> System.out.println("Exiting the Bookstore Management System");
                    default -> System.out.println("Invalid choice. Please enter a valid option.");
                }

                if (choice.equals("5")) {
                    break;
                }
            }

            DatabaseConnection.closeConnection(connection);
            System.out.println("Connection closed");
        } else {
            System.out.println("Failed to connect to the database");
        }
    }
}
