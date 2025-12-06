package com.project.smartshopmangement.service;

import com.project.smartshopmangement.model.Product;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductService {

    /*-----------------------------------------------------*/
    // Method to validate and save product
    public boolean addProductService(Product product) throws SQLException;

 // Method to fetch all products
    ArrayList<Product> getAllProductsService();
 // Add this method
    Product getProductByIdService(int productId);
//update the product
    boolean updateProductService(Product product);
    //delete the product
    boolean deleteProductService(int productId);
}