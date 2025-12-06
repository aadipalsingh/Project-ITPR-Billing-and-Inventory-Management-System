package com.project.smartshopmangement.dao;

import java.util.ArrayList;
import com.project.smartshopmangement.model.CartItem;
import com.project.smartshopmangement.model.Sale;

public interface SalesDAO {

    // The Big Method: Saves Sale + Saves Items + Updates Stock
    // Returns true if everything succeeds, false if anything fails

	boolean placeOrder(Sale sale, ArrayList<CartItem> cart);
	
	ArrayList<Sale> getAllSales();

	
    
}