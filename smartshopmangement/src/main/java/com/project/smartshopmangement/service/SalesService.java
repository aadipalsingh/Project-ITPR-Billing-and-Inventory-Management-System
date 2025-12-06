package com.project.smartshopmangement.service;

import java.util.ArrayList;
import com.project.smartshopmangement.model.CartItem;
import com.project.smartshopmangement.model.Customer;
import com.project.smartshopmangement.model.Sale;

public interface SalesService {

    // Main method to process the cart and checkout
	public boolean checkoutService(Customer customer, ArrayList<CartItem> cart);
	//fetch all the sales
	ArrayList<Sale> getAllSalesService();
    
}