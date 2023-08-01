package restaurantmanagement;

import java.awt.MenuItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RestaurantManagementApp {
    private static Scanner scanner = new Scanner(System.in);
    private static DatabaseConnector databaseConnector = new DatabaseConnector();

    public static void main(String[] args) {
        try {
            // Application logic goes here
            while (true) {
                System.out.println("------ Restaurant Management System ------");
                System.out.println("1. List Restaurants");
                System.out.println("2. Reserve a Table");
                System.out.println("3. List Menu Items");
                System.out.println("4. Place an Order");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        listRestaurants();
                        break;
                    case 2:
                        reserveTable();
                        break;
                    case 3:
                        listMenuItems();
                        break;
                    case 4:
                        placeOrder();
                        break;
                    case 5:
                        System.out.println("Thank you for using the Restaurant Management System. Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Don't forget to close the database connection at the end of the application
            databaseConnector.closeConnection();
        }
    }

    private static void listRestaurants() throws SQLException {
        Connection connection = databaseConnector.getConnection();
        Statement stmt = connection.createStatement();
        String query = "SELECT * FROM restaurants";
        ResultSet resultSet = stmt.executeQuery(query);

        List<Restaurant> restaurants = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String location = resultSet.getString("location");
            Restaurant restaurant = new Restaurant(id, name, location);
            restaurants.add(restaurant);
        }

        if (restaurants.isEmpty()) {
            System.out.println("No restaurants found.");
        } else {
            System.out.println("------ List of Restaurants ------");
            for (Restaurant restaurant : restaurants) {
                System.out.println(restaurant);
            }
        }

        resultSet.close();
        stmt.close();
    }

    private static void reserveTable() throws SQLException {
        System.out.print("Enter the restaurant ID: ");
        int restaurantId = scanner.nextInt();

        System.out.print("Enter the table number: ");
        int tableNumber = scanner.nextInt();

        System.out.print("Enter the customer name: ");
        String customerName = scanner.next();

        Connection connection = databaseConnector.getConnection();
        String query = "INSERT INTO reservations (restaurant_id, table_number, customer_name) VALUES (?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, restaurantId);
        pstmt.setInt(2, tableNumber);
        pstmt.setString(3, customerName);

        int rowsAffected = pstmt.executeUpdate();
        pstmt.close();

        if (rowsAffected > 0) {
            System.out.println("Table reservation successful!");
        } else {
            System.out.println("Table reservation failed. Please try again.");
        }
    }

    private static void listMenuItems() throws SQLException {
        System.out.print("Enter the restaurant ID: ");
        int restaurantId = scanner.nextInt();

        Connection connection = databaseConnector.getConnection();
        String query = "SELECT * FROM menu_items WHERE restaurant_id = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, restaurantId);

        ResultSet resultSet = pstmt.executeQuery();
        List<MenuItem> menuItems = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            double price = resultSet.getDouble("price");
            MenuItem menuItem = new MenuItem(name);
            menuItems.add(menuItem);
        }

        if (menuItems.isEmpty()) {
            System.out.println("No menu items found for the restaurant.");
        } else {
            System.out.println("------ Menu Items ------");
            for (MenuItem menuItem : menuItems) {
                System.out.println(menuItem);
            }
        }

        resultSet.close();
        pstmt.close();
    }

    private static void placeOrder() throws SQLException {
        System.out.print("Enter the restaurant ID: ");
        int restaurantId = scanner.nextInt();

        System.out.print("Enter the table number: ");
        int tableNumber = scanner.nextInt();

        System.out.print("Enter the customer name: ");
        String customerName = scanner.next();

        System.out.print("Enter the menu item ID to order: ");
        int menuItemId = scanner.nextInt();

        Connection connection = databaseConnector.getConnection();

        // Check if the menu item exists and get its price
        String menuItemQuery = "SELECT price FROM menu_items WHERE restaurant_id = ? AND id = ?";
        PreparedStatement menuItemPstmt = connection.prepareStatement(menuItemQuery);
        menuItemPstmt.setInt(1, restaurantId);
        menuItemPstmt.setInt(2, menuItemId);
        ResultSet menuItemResultSet = menuItemPstmt.executeQuery();

        if (!menuItemResultSet.next()) {
            System.out.println("Menu item not found. Please try again.");
            menuItemResultSet.close();
            menuItemPstmt.close();
            return;
        }

        double price = menuItemResultSet.getDouble("price");
        menuItemResultSet.close();
        menuItemPstmt.close();

        // Insert the order into the orders table
        String orderQuery = "INSERT INTO orders (restaurant_id, table_number, customer_name, menu_item_id, price) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement orderPstmt = connection.prepareStatement(orderQuery);
        orderPstmt.setInt(1, restaurantId);
        orderPstmt.setInt(2, tableNumber);
        orderPstmt.setString(3, customerName);
        orderPstmt.setInt(4, menuItemId);
        orderPstmt.setDouble(5, price);

        int rowsAffected = orderPstmt.executeUpdate();
        orderPstmt.close();

        if (rowsAffected > 0) {
            System.out.println("Order placed successfully!");
        } else {
            System.out.println("Order placement failed. Please try again.");
        }
    }
}
