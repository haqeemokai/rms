package com.example.rms;

import com.example.rms.models.Customer;
import com.example.rms.models.MenuItem;
import com.example.rms.models.Order;
import com.example.rms.models.OrderItem;
import com.example.rms.models.User;
import com.example.rms.services.CustomerService;
import com.example.rms.services.MenuService;
import com.example.rms.services.OrderService;
import com.example.rms.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class RMSMenu {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(RMSMenu.class, args);

        UserService userService = context.getBean(UserService.class);
        CustomerService customerService = context.getBean(CustomerService.class);
        MenuService menuService = context.getBean(MenuService.class);
        OrderService orderService = context.getBean(OrderService.class);

        runMenu(userService, customerService, menuService, orderService);
    }

    private static void runMenu(UserService userService, CustomerService customerService, MenuService menuService, OrderService orderService) {
        Scanner scanner = new Scanner(System.in);

        // Authentication process
        if (!authenticateUser(scanner, userService)) {
            System.out.println("Exiting the system...");
            return;
        }

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

    private static boolean authenticateUser(Scanner scanner, UserService userService) {
        System.out.println("===== Welcome to the Restaurant Management System =====");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        switch (choice) {
            case 1:
                System.out.print("Enter username: ");
                String regUsername = scanner.nextLine();
                System.out.print("Enter password: ");
                String regPassword = scanner.nextLine();
                System.out.print("Enter role (e.g., ADMIN): ");
                String regRole = scanner.nextLine();

                User newUser = new User();
                newUser.setUsername(regUsername);
                newUser.setPassword(regPassword);
                newUser.setRole(regRole);

                userService.register(newUser);
                System.out.println("User registered successfully!");
                return authenticateUser(scanner, userService); // Return to login after registration

            case 2:
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                Optional<User> userOptional = userService.login(username, password);
                if (userOptional.isPresent()) {
                    System.out.println("Login successful! Welcome, " + userOptional.get().getUsername());
                    return true;
                } else {
                    System.out.println("Invalid credentials. Try again.");
                    return authenticateUser(scanner, userService);
                }

            case 3:
                return false;

            default:
                System.out.println("Invalid choice. Try again.");
                return authenticateUser(scanner, userService);
        }
    }

    private static void manageCustomers(Scanner scanner, CustomerService customerService) {
        System.out.println("=== Manage Customers ===");
        System.out.println("1. Register New Customer");
        System.out.println("2. Update Customer Info");
        System.out.println("3. View Customers");
        System.out.println("4. Go Back");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        switch (choice) {
            case 1:
                System.out.print("Enter customer ID: ");
                String id = scanner.nextLine();
                System.out.print("Enter customer name: ");
                String name = scanner.nextLine();
                System.out.print("Enter customer email: ");
                String email = scanner.nextLine();
                System.out.print("Enter customer phone: ");
                String phone = scanner.nextLine();
                System.out.print("Enter customer address: ");
                String address = scanner.nextLine();

                Customer newCustomer = new Customer();
                newCustomer.setId(id);
                newCustomer.setName(name);
                newCustomer.setEmail(email);
                newCustomer.setPhone(phone);
                newCustomer.setAddress(address);

                try {
                    customerService.registerCustomer(newCustomer);
                    System.out.println("Customer registered successfully!");
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;

            case 2:
                System.out.print("Enter customer ID to update: ");
                String updateId = scanner.nextLine();
                Optional<Customer> existingCustomer = customerService.findCustomerById(updateId);
                if (existingCustomer.isPresent()) {
                    Customer customerToUpdate = existingCustomer.get();

                    System.out.print("Enter new name (or press Enter to keep current): ");
                    String updatedName = scanner.nextLine();
                    if (!updatedName.isEmpty()) customerToUpdate.setName(updatedName);

                    System.out.print("Enter new email (or press Enter to keep current): ");
                    String updatedEmail = scanner.nextLine();
                    if (!updatedEmail.isEmpty()) customerToUpdate.setEmail(updatedEmail);

                    System.out.print("Enter new phone (or press Enter to keep current): ");
                    String updatedPhone = scanner.nextLine();
                    if (!updatedPhone.isEmpty()) customerToUpdate.setPhone(updatedPhone);

                    System.out.print("Enter new address (or press Enter to keep current): ");
                    String updatedAddress = scanner.nextLine();
                    if (!updatedAddress.isEmpty()) customerToUpdate.setAddress(updatedAddress);

                    customerService.updateCustomer(updateId, customerToUpdate);
                    System.out.println("Customer information updated successfully!");
                } else {
                    System.out.println("Customer not found.");
                }
                break;

            case 3:
                System.out.println("Viewing all customers:");
                customerService.getAllCustomers().forEach(System.out::println);
                break;

            case 4:
                System.out.println("Returning to main menu...");
                break;

            default:
                System.out.println("Invalid choice. Returning to main menu...");
        }
    }

    private static void manageMenuItems(Scanner scanner, MenuService menuService) {
        System.out.println("=== Manage Menu Items ===");
        System.out.println("1. Add Menu Item");
        System.out.println("2. Update Menu Item");
        System.out.println("3. View Menu Items");
        System.out.println("4. Go Back");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        switch (choice) {
            case 1:
                System.out.print("Enter menu item ID: ");
                String id = scanner.nextLine();
                System.out.print("Enter menu item name: ");
                String name = scanner.nextLine();
                System.out.print("Enter menu item description: ");
                String description = scanner.nextLine();
                System.out.print("Enter menu item price: ");
                double price = scanner.nextDouble();
                scanner.nextLine();
                System.out.print("Is the menu item available (true/false): ");
                boolean available = scanner.nextBoolean();
                scanner.nextLine();

                MenuItem newMenuItem = new MenuItem();
                newMenuItem.setId(id);
                newMenuItem.setName(name);
                newMenuItem.setDescription(description);
                newMenuItem.setPrice(price);
                newMenuItem.setAvailable(available);

                try {
                    menuService.addMenuItem(newMenuItem);
                    System.out.println("Menu item added successfully!");
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;

            case 2:
                System.out.print("Enter menu item ID to update: ");
                String updateMenuId = scanner.nextLine();
                Optional<MenuItem> existingMenuItem = menuService.getMenuItemById(updateMenuId);
                if (existingMenuItem.isPresent()) {
                    MenuItem menuItemToUpdate = existingMenuItem.get();

                    System.out.print("Enter new name (or press Enter to keep current): ");
                    String updatedName = scanner.nextLine();
                    if (!updatedName.isEmpty()) menuItemToUpdate.setName(updatedName);

                    System.out.print("Enter new description (or press Enter to keep current): ");
                    String updatedDescription = scanner.nextLine();
                    if (!updatedDescription.isEmpty()) menuItemToUpdate.setDescription(updatedDescription);

                    System.out.print("Enter new price (or press Enter to keep current): ");
                    String priceInput = scanner.nextLine();
                    if (!priceInput.isEmpty()) menuItemToUpdate.setPrice(Double.parseDouble(priceInput));

                    System.out.print("Is the menu item available (true/false) (or press Enter to keep current): ");
                    String availableInput = scanner.nextLine();
                    if (!availableInput.isEmpty()) menuItemToUpdate.setAvailable(Boolean.parseBoolean(availableInput));

                    menuService.updateMenuItem(updateMenuId, menuItemToUpdate);
                    System.out.println("Menu item updated successfully!");
                } else {
                    System.out.println("Menu item not found.");
                }
                break;

            case 3:
                System.out.println("Viewing all menu items:");
                menuService.getAllMenuItems().forEach(System.out::println);
                break;

            case 4:
                System.out.println("Returning to main menu...");
                break;

            default:
                System.out.println("Invalid choice. Returning to main menu...");
        }
    }

    private static void manageOrders(Scanner scanner, OrderService orderService) {
        System.out.println("=== Manage Orders ===");
        System.out.println("1. Create Order");
        System.out.println("2. Update Order Status");
        System.out.println("3. View Order History");
        System.out.println("4. Go Back");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Enter order ID: ");
                String orderId = scanner.nextLine();
                System.out.print("Enter customer ID: ");
                String customerId = scanner.nextLine();

                List<OrderItem> items = new ArrayList<>();
                boolean addingItems = true;

                while (addingItems) {
                    System.out.print("Enter menu item ID: ");
                    String menuItemId = scanner.nextLine();
                    System.out.print("Enter menu item name: ");
                    String menuItemName = scanner.nextLine();
                    System.out.print("Enter quantity: ");
                    int quantity = scanner.nextInt();
                    System.out.print("Enter price: ");
                    double price = scanner.nextDouble();
                    scanner.nextLine();

                    OrderItem item = new OrderItem();
                    item.setMenuItemId(menuItemId);
                    item.setName(menuItemName);
                    item.setQuantity(quantity);
                    item.setPrice(price);

                    items.add(item);

                    System.out.print("Add another item? (yes/no): ");
                    addingItems = scanner.nextLine().equalsIgnoreCase("yes");
                }

                Order newOrder = new Order();
                newOrder.setId(orderId);
                newOrder.setCustomerId(customerId);
                newOrder.setItems(items);

                try {
                    orderService.createOrder(newOrder);
                    System.out.println("Order created successfully!");
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;

            case 2:
                System.out.print("Enter order ID: ");
                String updateOrderId = scanner.nextLine();
                System.out.print("Enter new status: ");
                String status = scanner.nextLine();

                Optional<Order> updatedOrder = orderService.updateOrderStatus(updateOrderId, status);
                if (updatedOrder.isPresent()) {
                    System.out.println("Order updated successfully!");
                } else {
                    System.out.println("Order not found.");
                }
                break;

            case 3:
                System.out.print("Enter customer ID: ");
                String customerForHistory = scanner.nextLine();
                orderService.getOrderHistory(customerForHistory).forEach(System.out::println);
                break;

            case 4:
                System.out.println("Returning to main menu...");
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }
}
