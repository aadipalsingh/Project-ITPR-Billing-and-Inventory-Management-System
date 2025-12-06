package com.project.smartshopmangement.dao;

import com.project.smartshopmangement.model.Product;
import java.util.ArrayList; // Import ArrayList

public interface ProductDAO {

    // method to insert a new product
    boolean addProduct(Product product);

    // method to fetch all products
    ArrayList<Product> getAllProducts();
    
 // Update existing product
    boolean updateProduct(Product product);

    // Delete product by ID
    boolean deleteProduct(int productId);

	

}