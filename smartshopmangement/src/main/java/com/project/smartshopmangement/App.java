package com.project.smartshopmangement;

import java.util.Scanner;
import com.project.smartshopmangement.controller.AdminController;
import com.project.smartshopmangement.controller.CustomerController;
import com.project.smartshopmangement.util.DataBaseUtil;

public class App {
    
    public static void main(String[] args) {
        // Init Controllers
        AdminController adminController = new AdminController();
        CustomerController customerController = new CustomerController();
        Scanner sc = new Scanner(System.in);

        System.out.println("🔄 System Init...");
        
        // Database Connection Check
        try {
            DataBaseUtil.establishConnection();
        } catch (Exception e) {
            System.out.println("❌ DB Error: " + e.getMessage());
            return; // Stop if no DB
        }

        // Main Application Loop
        while (true) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Admin Access");
            System.out.println("2. Customer Access");
            System.out.println("3. Exit");
            System.out.print("👉 Select: ");

            // Input Validation
            if (!sc.hasNextInt()) { sc.next(); continue; }
            int choice = sc.nextInt();

            // Routing
            switch (choice) {
                case 1: adminController.adminLoginUI(); break;
                case 2: customerController.customerPanelUI(); break;
                case 3: 
                    System.out.println("👋 Bye!"); 
                    System.exit(0);
                default: System.out.println("❌ Invalid");
            }
        }
    }
}