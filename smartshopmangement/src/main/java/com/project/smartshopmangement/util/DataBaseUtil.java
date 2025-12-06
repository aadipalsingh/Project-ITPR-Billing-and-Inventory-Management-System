package com.project.smartshopmangement.util;

import java.sql.*;

public class DataBaseUtil {

    // 1. Database Configuration
    private static final String url = "jdbc:mysql://localhost:3306/smartshop_management";
    private static final String user = "root";
    private static final String password = "0011"; 

    private static Connection con = null;

    // 2. Method to Establish Connection
    public static Connection establishConnection() {
        try {
            // Load Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Connect to the specific database 
            con = DriverManager.getConnection(url, user, password);
            
            // Check and Create Tables
            createTables();
            
        } catch (Exception e) {
            System.err.println("❌ Connection Failed: " + e.getMessage());
        }
        return con;
    }

    // 3. Method to Close Connection
    public static void closeConnection() {
        try {
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4. Private Method to Create All 8 Tables
    private static void createTables() throws SQLException {
        Statement stmt = con.createStatement();

        // --- A. MASTER DATA TABLES ---
        
        String t1_Users = "CREATE TABLE IF NOT EXISTS users ("
                + "user_id INT AUTO_INCREMENT PRIMARY KEY, "
                + "username VARCHAR(50) UNIQUE NOT NULL, "
                + "password VARCHAR(50) NOT NULL, "
                + "role VARCHAR(20) NOT NULL)";

        String t2_Categories = "CREATE TABLE IF NOT EXISTS categories ("
                + "category_id INT AUTO_INCREMENT PRIMARY KEY, "
                + "name VARCHAR(50) NOT NULL, "
                + "description VARCHAR(100))";

        String t3_Brands = "CREATE TABLE IF NOT EXISTS brands ("
                + "brand_id INT AUTO_INCREMENT PRIMARY KEY, "
                + "brand_name VARCHAR(50) NOT NULL)";

        String t4_Taxes = "CREATE TABLE IF NOT EXISTS taxes ("
                + "tax_id INT AUTO_INCREMENT PRIMARY KEY, "
                + "tax_name VARCHAR(20), "
                + "rate DOUBLE)";

        // --- B. INVENTORY & CUSTOMER TABLES ---

        String t5_Products = "CREATE TABLE IF NOT EXISTS products ("
                + "product_id INT AUTO_INCREMENT PRIMARY KEY, "
                + "name VARCHAR(100) NOT NULL, "
                + "price DOUBLE NOT NULL, "
                + "quantity INT DEFAULT 0, "
                + "category_id INT, "
                + "brand_id INT, "
                + "tax_id INT, "
                + "FOREIGN KEY (category_id) REFERENCES categories(category_id), "
                + "FOREIGN KEY (brand_id) REFERENCES brands(brand_id), "
                + "FOREIGN KEY (tax_id) REFERENCES taxes(tax_id))";

        String t6_Customers = "CREATE TABLE IF NOT EXISTS customers ("
                + "customer_id INT AUTO_INCREMENT PRIMARY KEY, "
                + "name VARCHAR(100), "
                + "phone VARCHAR(15) UNIQUE, "
                + "email VARCHAR(100), "
                + "password VARCHAR(50) NOT NULL)"; // Added Password for Login

        // --- C. TRANSACTION TABLES (Sales & Cart Items) ---

        String t7_Sales = "CREATE TABLE IF NOT EXISTS sales ("
                + "sale_id INT AUTO_INCREMENT PRIMARY KEY, "
                + "sale_date DATETIME DEFAULT CURRENT_TIMESTAMP, "
                + "total_amount DOUBLE, "
                + "customer_id INT, "
                + "FOREIGN KEY (customer_id) REFERENCES customers(customer_id))";

        String t8_CartItems = "CREATE TABLE IF NOT EXISTS cart_items ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "sale_id INT, "
                + "product_id INT, "
                + "qty INT, "
                + "price_at_sale DOUBLE, "
                + "FOREIGN KEY (sale_id) REFERENCES sales(sale_id), "
                + "FOREIGN KEY (product_id) REFERENCES products(product_id))";

        // --- D. DEFAULT DATA (Admin Setup) ---
        String adminData = "INSERT IGNORE INTO users (user_id, username, password, role) VALUES (1, 'admin', 'admin123', 'ADMIN')";
        String taxData = "INSERT IGNORE INTO taxes (tax_id, tax_name, rate) VALUES (1, 'GST 18%', 18.0)";

        // Execute Batch
        stmt.addBatch(t1_Users);
        stmt.addBatch(t2_Categories);
        stmt.addBatch(t3_Brands);
        stmt.addBatch(t4_Taxes);
        stmt.addBatch(t5_Products);
        stmt.addBatch(t6_Customers);
        stmt.addBatch(t7_Sales);
        stmt.addBatch(t8_CartItems);
        
        stmt.addBatch(adminData);
        stmt.addBatch(taxData);

        stmt.executeBatch();
        return;
    }
}