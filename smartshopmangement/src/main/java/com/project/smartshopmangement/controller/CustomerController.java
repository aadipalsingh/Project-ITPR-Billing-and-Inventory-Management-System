package com.project.smartshopmangement.controller;

import java.util.ArrayList;
import java.util.Scanner;
import com.project.smartshopmangement.model.CartItem;
import com.project.smartshopmangement.model.Customer;
import com.project.smartshopmangement.model.Product;
import com.project.smartshopmangement.service.CustomerService;
import com.project.smartshopmangement.service.ProductService;
import com.project.smartshopmangement.service.SalesService;
import com.project.smartshopmangement.service.impl.CustomerServiceImpl;
import com.project.smartshopmangement.service.impl.ProductServiceImpl;
import com.project.smartshopmangement.service.impl.SalesServiceImpl;

public class CustomerController {

    private CustomerService customerService;
    private ProductService productService;
    private SalesService salesService;
    private Scanner sc;

    public CustomerController() {
        customerService = new CustomerServiceImpl();
        productService = new ProductServiceImpl();
        salesService = new SalesServiceImpl();
        sc = new Scanner(System.in);
    }

    public void customerPanelUI() {
        System.out.println("\n--- CUSTOMER PANEL ---");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Back");
        System.out.print("👉 Select: ");
        int op = sc.nextInt();

        if (op == 1) {
            System.out.print("Phone: "); String ph = sc.next();
            System.out.print("Password: "); String pw = sc.next();
            Customer c = customerService.loginCustomer(ph, pw);
            if (c != null) shoppingUI(c);
            else System.out.println("❌ Login Failed");
        } else if (op == 2) {
            System.out.println("Enter Name, Phone, Email, Pass: ");
            sc.nextLine(); 
            customerService.registerCustomer(new Customer(sc.nextLine(), sc.next(), sc.next(), sc.next()));
        }
    }

    private void shoppingUI(Customer c) {
        ArrayList<CartItem> cart = new ArrayList<>();
        boolean active = true;
        
        while (active) {
            System.out.println("\nWelcome " + c.getName() + " | Cart Items: " + cart.size());
            System.out.println("1. Browse Products");
            System.out.println("2. Add to Cart");
            System.out.println("3. View Cart & Checkout");
            System.out.println("4. Logout");
            System.out.print("👉 Select: ");
            int op = sc.nextInt();

            switch (op) {
                case 1:
                    // --- TABLE FORMAT: BROWSE PRODUCTS ---
                    ArrayList<Product> list = productService.getAllProductsService();
                    System.out.println("\n+-----+----------------------+-----------+------+");
                    System.out.printf("| %-3s | %-20s | %-9s | %-4s |\n", "ID", "NAME", "PRICE", "QTY");
                    System.out.println("+-----+----------------------+-----------+------+");
                    for (Product p : list) {
                        System.out.printf("| %-3d | %-20s | %-9.2f | %-4d |\n", 
                            p.getProductId(), p.getName(), p.getPrice(), p.getQuantity());
                    }
                    System.out.println("+-----+----------------------+-----------+------+");
                    break;
                    
                case 2:
                    System.out.print("Prod ID: "); int pid = sc.nextInt();
                    Product p = productService.getProductByIdService(pid);
                    
                    if(p != null) {
                        System.out.print("Qty (Avail: "+p.getQuantity()+"): "); 
                        int qty = sc.nextInt();
                        if(qty <= p.getQuantity()) {
                            cart.add(new CartItem(pid, qty, p.getPrice() * qty));
                            System.out.println("✅ Added to Cart");
                        } else System.out.println("❌ Low Stock");
                    } else System.out.println("❌ Invalid ID");
                    break;
                    
                case 3:
                    // --- TABLE FORMAT: CART & CHECKOUT ---
                    if(cart.isEmpty()) {
                        System.out.println("⚠️ Cart is Empty");
                    } else {
                        System.out.println("\n------------- YOUR CART -------------");
                        System.out.println("+-----+----------------------+----------+-----------+");
                        System.out.printf("| %-3s | %-20s | %-8s | %-9s |\n", "ID", "ITEM NAME", "QTY", "SUBTOTAL");
                        System.out.println("+-----+----------------------+----------+-----------+");
                        
                        double total = 0;
                        for(CartItem item : cart) {
                            Product prod = productService.getProductByIdService(item.getProductId());
                            String pName = (prod != null) ? prod.getName() : "Unknown";
                            
                            System.out.printf("| %-3d | %-20s | %-8d | %-9.2f |\n", 
                                    item.getProductId(), pName, item.getQuantity(), item.getSubTotal());
                            total += item.getSubTotal();
                        }
                        System.out.println("+-----+----------------------+----------+-----------+");
                        System.out.printf("| %33s | Rs.%-9.2f |\n", "GRAND TOTAL", total);
                        System.out.println("+---------------------------------------+-----------+");
                        
                        System.out.println("\n[ OPTIONS ]");
                        System.out.println(" 1. ✅ Confirm & Pay");
                        System.out.println(" 0. ❌ Cancel & Go Back");
                        System.out.print("👉 Select: ");
                        
                        int choice = sc.nextInt();
                        
                        if(choice == 1) {
                            // YES - Proceed to Buy
                            if(salesService.checkoutService(c, cart)) {
                                System.out.println("\n🎉 PAYMENT SUCCESSFUL!");
                                System.out.println("📄 Invoice Generated. Thank you for shopping!");
                                cart.clear(); // Clear cart only on success
                            } else {
                                System.out.println("❌ Transaction Failed. Please try again.");
                            }
                        } else {
                            // NO - Cancel
                            System.out.println("\n🚫 Checkout Cancelled. Items remain in cart.");
                        }
                        
                    }
                case 4: active = false;
            }
        }
    }
}