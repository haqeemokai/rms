package com.example.rms;

import com.example.rms.models.Customer;
import com.example.rms.models.MenuItem;
import com.example.rms.models.Order;
import com.example.rms.services.CustomerService;
import com.example.rms.services.MenuService;
import com.example.rms.services.OrderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class RMSMenu {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(RMSMenu.class, args);

        CustomerService customerService = context.getBean(CustomerService.class);
        MenuService menuService = context.getBean(MenuService.class);
        OrderService orderService = context.getBean(OrderService.class);

        runMenu(customerService, menuService, orderService);
    }

    private static void runMenu(CustomerService customerService, MenuService menuService, OrderService orderService) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("===== Restaurant Management System =====");
            System.out.println("1. Manage Customers");
            System.out.println("2. Manage Menu Items");
            System.out.println("3. Manage Orders");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    manageCustomers(scanner, customerService);
                    break;
                case 2:
                    manageMenuItems(scanner, menuService);
                    break;
                case 3:
                    manageOrders(scanner, orderService);
                    break;
                case 4:
                    running = false;
                    System.out.println("Exiting the system...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void manageCustomers(Scanner scanner, CustomerService customerService) {
        System.out.println("=== Manage Customers ===");
        System.out.println("1. Register New Customer");
        System.out.println("2. View Customers");
        System.out.println("3. Go Back");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        switch (choice) {
            case 1:
                System.out.print("Enter customer name: ");
                String name = scanner.nextLine();
                System.out.print("Enter customer email: ");
                String email = scanner.nextLine();
                System.out.print("Enter customer phone: ");
                String phone = scanner.nextLine();
                System.out.print("Enter customer address: ");
                String address = scanner.nextLine();

                Customer newCustomer = new Customer();
                newCustomer.setName(name);
                newCustomer.setEmail(email);
                newCustomer.setPhone(phone);
                newCustomer.setAddress(address);

                customerService.registerCustomer(newCustomer);
                System.out.println("Customer registered successfully!");
                break;

            case 2:
                System.out.println("Viewing all customers:");
                customerService.getAllCustomers().forEach(System.out::println);
                break;

            case 3:
                System.out.println("Returning to main menu...");
                break;

            default:
                System.out.println("Invalid choice. Returning to main menu...");
        }
    }

    private static void manageMenuItems(Scanner scanner, MenuService menuService) {
        System.out.println("=== Manage Menu Items ===");
        System.out.println("1. Add Menu Item");
        System.out.println("2. View Menu Items");
        System.out.println("3. Go Back");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        switch (choice) {
            case 1:
                System.out.print("Enter menu item name: ");
                String itemName = scanner.nextLine();
                System.out.print("Enter menu item description: ");
                String itemDescription = scanner.nextLine();
                System.out.print("Enter menu item price: ");
                double price = scanner.nextDouble();
                scanner.nextLine(); // Consume newline character

                MenuItem menuItem = new MenuItem();
                menuItem.setName(itemName);
                menuItem.setDescription(itemDescription);
                menuItem.setPrice(price);
                menuItem.setAvailable(true);

                menuService.addMenuItem(menuItem);
                System.out.println("Menu item added successfully!");
                break;

            case 2:
                System.out.println("Viewing all menu items:");
                menuService.getAllMenuItems().forEach(System.out::println);
                break;

            case 3:
                System.out.println("Returning to main menu...");
                break;

            default:
                System.out.println("Invalid choice. Returning to main menu...");
        }
    }

    private static void manageOrders(Scanner scanner, OrderService orderService) {
        System.out.println("=== Manage Orders ===");
        System.out.println("1. Create Order");
        System.out.println("2. View Order History");
        System.out.println("3. Go Back");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        switch (choice) {
            case 1:
                System.out.println("Creating a new order...");
                // Simplified for brevity; you can add input for customer ID and items
                break;

            case 2:
                System.out.print("Enter customer ID: ");
                String customerId = scanner.nextLine();
                System.out.println("Order history for customer:");
                orderService.getOrderHistory(customerId).forEach(System.out::println);
                break;

            case 3:
                System.out.println("Returning to main menu...");
                break;

            default:
                System.out.println("Invalid choice. Returning to main menu...");
        }
    }
}
