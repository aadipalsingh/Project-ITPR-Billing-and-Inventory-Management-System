package com.project.smartshopmangement.service.impl;

import java.sql.Date;
import java.util.ArrayList;

import com.project.smartshopmangement.dao.SalesDAO;
import com.project.smartshopmangement.dao.impl.SalesDAOImpl;
import com.project.smartshopmangement.model.CartItem;
import com.project.smartshopmangement.model.Customer;
import com.project.smartshopmangement.model.Sale;
import com.project.smartshopmangement.service.SalesService;

public class SalesServiceImpl implements SalesService {

    private SalesDAO salesDAO = new SalesDAOImpl();

    @Override
    public boolean checkoutService(Customer customer, ArrayList<CartItem> cart) {
        
        // 1. Validation
        if(cart == null || cart.isEmpty()) {
            System.out.println("❌ Cart is Empty!");
            return false;
        }
        
        // 2. Calculate Grand Total
        double grandTotal = 0;
        for(CartItem item : cart) {
            grandTotal += item.getSubTotal();
        }
        
        // 3. Prepare the Sale Object (Bill Header)
        Sale sale = new Sale();
        sale.setCustomerId(customer.getCustomerId());
        sale.setTotalAmount(grandTotal);
        
        // Get current date for the bill
        long millis = System.currentTimeMillis();
        sale.setSaleDate(new Date(millis));
        
        // 4. Call DAO (Exceptions are handled inside DAO now)
        return salesDAO.placeOrder(sale, cart);
    }
    
    @Override
    public ArrayList<Sale> getAllSalesService() {
        return salesDAO.getAllSales();
    }
}