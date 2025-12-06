package com.project.smartshopmangement.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import com.project.smartshopmangement.model.Product;
import com.project.smartshopmangement.model.Sale;
import com.project.smartshopmangement.model.Admin;
import com.project.smartshopmangement.service.ProductService;
import com.project.smartshopmangement.service.SalesService;
import com.project.smartshopmangement.service.AdminService;
import com.project.smartshopmangement.service.impl.ProductServiceImpl;
import com.project.smartshopmangement.service.impl.SalesServiceImpl;
import com.project.smartshopmangement.service.impl.AdminServiceImpl;

public class AdminController {

    // Dependencies
    private AdminService userService;
    private ProductService productService;
    private SalesService salesService;
    private Scanner sc;

    // Constructor: Initialize services and scanner
    public AdminController() {
        userService = new AdminServiceImpl();
        productService = new ProductServiceImpl();
        salesService = new SalesServiceImpl();
        sc = new Scanner(System.in);
    }

    // Method: Login UI for Admin
    public void adminLoginUI() {
        System.out.println("\n--- 🔐 ADMIN LOGIN ---");
        System.out.print("Username: ");
        String u = sc.next();
        System.out.print("Password: ");
        String p = sc.next();

        // Validation Logic using Service Layer
        try {
            Admin user = userService.loginService(u, p);
            if (user != null && "ADMIN".equalsIgnoreCase(user.getRole())) {
                adminDashboardUI(); // Login Success -> Go to Dashboard
            } else {
                System.out.println("❌ Access Denied.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Method: Main Dashboard Loop for Admin features
    public void adminDashboardUI() {
        boolean active = true;
        while (active) {
            System.out.println("\n=== ADMIN DASHBOARD ===");
            System.out.println(" 1. ➕ Add Product");
            System.out.println(" 2. ✏️  Update Product");
            System.out.println(" 3. 🗑️  Delete Product");
            System.out.println(" 4. 📦 View Inventory");
            System.out.println(" 5. 💰 View Sales History");
            System.out.println(" 6. 🔙 Logout");
            System.out.print("👉 Option: ");

            if (!sc.hasNextInt()) { 
                sc.next(); // Clear invalid input
                continue; 
            }
            int op = sc.nextInt();

            // Menu Routing
            switch (op) {
                case 1: addProductUI(); break;
                case 2: updateProductUI(); break;
                case 3: deleteProductUI(); break;
                case 4: viewInventoryUI(); break;
                case 5: viewSalesHistoryUI(); break;
                case 6: active = false; break;
                default: System.out.println("❌ Invalid Choice");
            }
        }
    }

    // Feature: Add a new Product
    private void addProductUI() {
        System.out.println("\n[ Add New Product ]");
        System.out.print("Name: "); 
        String name = sc.next();
        System.out.print("Price: "); 
        double price = sc.nextDouble();
        System.out.print("Qty: "); 
        int qty = sc.nextInt();
        
        // Input foreign keys for linking
        System.out.print("Category ID (1-Elec, 2-Cloth, 3-Groc): "); 
        int catId = sc.nextInt();
        System.out.print("Brand ID (1-Sam, 2-Ni, 4-Gen): "); 
        int brandId = sc.nextInt();
        System.out.print("Tax ID (1-18%, 2-12%, 4-0%): "); 
        int taxId = sc.nextInt();

        // Call Service to add product
        try {
			if (productService.addProductService(new Product(name, price, qty, catId, brandId, taxId))) {
			    System.out.println("✅ Product Added!");
			} else {
			    System.out.println("❌ Failed to add product.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    // Feature: Update an existing Product
    private void updateProductUI() {
        viewInventoryUI(); // Show list first so admin knows ID
        System.out.print("\nEnter ID to Update: ");
        int pid = sc.nextInt();
        
        // Check existence
        Product p = productService.getProductByIdService(pid);
        if (p != null) {
            System.out.println("Enter 'SAME' to keep old value.");
            
            System.out.print("Name [" + p.getName() + "]: ");
            String n = sc.next();
            if (!n.equalsIgnoreCase("SAME")) p.setName(n);

            System.out.print("Price [" + p.getPrice() + "]: ");
            if (sc.hasNextDouble()) {
                 p.setPrice(sc.nextDouble());
            } else {
                 sc.next(); // Skip if user enters non-number (could handle better in real app)
            }

            System.out.print("Qty [" + p.getQuantity() + "]: ");
            if (sc.hasNextInt()) {
                p.setQuantity(sc.nextInt());
            } else {
                sc.next();
            }
            
            // Required ID inputs for update
            System.out.print("Category ID [" + p.getCategoryId() + "]: "); 
            if (sc.hasNextInt()) p.setCategoryId(sc.nextInt()); else sc.next();
            
            System.out.print("Brand ID [" + p.getBrandId() + "]: "); 
            if (sc.hasNextInt()) p.setBrandId(sc.nextInt()); else sc.next();
            
            System.out.print("Tax ID [" + p.getTaxId() + "]: "); 
            if (sc.hasNextInt()) p.setTaxId(sc.nextInt()); else sc.next();

            // Call Service
            if (productService.updateProductService(p)) System.out.println("✅ Updated!");
            else System.out.println("❌ Update Failed.");
        } else {
            System.out.println("❌ Not Found");
        }
    }

    // Feature: Delete a Product
    private void deleteProductUI() {
        System.out.print("Enter ID to Delete: ");
        int pid = sc.nextInt();
        // Call Service
        if (productService.deleteProductService(pid)) System.out.println("✅ Deleted.");
        else System.out.println("❌ Failed.");
    }

    // Feature: View Inventory Table
    private void viewInventoryUI() {
        ArrayList<Product> list = productService.getAllProductsService();
        if (list.isEmpty()) {
            System.out.println("⚠️ Inventory Empty.");
            return;
        }
        System.out.println("\n+-----+----------------------+-----------+------+-----+-----+");
        System.out.printf("| %-3s | %-20s | %-9s | %-4s | %-3s | %-3s |\n", "ID", "NAME", "PRICE", "QTY", "CAT", "BRD");
        System.out.println("+-----+----------------------+-----------+------+-----+-----+");
        for (Product p : list) {
            System.out.printf("| %-3d | %-20s | %-9.2f | %-4d | %-3d | %-3d |\n", 
                p.getProductId(), p.getName(), p.getPrice(), p.getQuantity(), p.getCategoryId(), p.getBrandId());
        }
        System.out.println("+-----+----------------------+-----------+------+-----+-----+");
    }

    // Feature: View Sales History Table
    private void viewSalesHistoryUI() {
        ArrayList<Sale> list = salesService.getAllSalesService();
        if (list.isEmpty()) {
            System.out.println("⚠️ No Sales Yet.");
            return;
        }
        
        System.out.println("\n+-----+---------+------------+------------+");
        System.out.printf("| %-3s | %-7s | %-10s | %-10s |\n", "ID", "CUST_ID", "DATE", "AMOUNT");
        System.out.println("+-----+---------+------------+------------+");
        
        double totalRevenue = 0;
        for (Sale s : list) {
            System.out.printf("| %-3d | %-7d | %-10s | Rs.%-7.2f |\n", 
                s.getSaleId(), s.getCustomerId(), s.getSaleDate(), s.getTotalAmount());
            totalRevenue += s.getTotalAmount();
        }
        System.out.println("+-----+---------+------------+------------+");
        System.out.println(" 💰 TOTAL REVENUE: Rs." + totalRevenue);
        System.out.println("-------------------------------------------");
    }
}