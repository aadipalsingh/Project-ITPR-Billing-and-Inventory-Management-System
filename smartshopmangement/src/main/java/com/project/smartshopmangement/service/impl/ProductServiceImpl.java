package com.project.smartshopmangement.service.impl;

import java.util.ArrayList;

import com.project.smartshopmangement.dao.ProductDAO;
import com.project.smartshopmangement.dao.impl.ProductDAOImpl;
import com.project.smartshopmangement.model.Product;
import com.project.smartshopmangement.service.ProductService;

public class ProductServiceImpl implements ProductService {

    private ProductDAO productDAO = new ProductDAOImpl();

    @Override
    public boolean addProductService(Product product) {
        // Validation Logic
        if(product.getName() == null || product.getName().isEmpty()) {
            System.out.println("Product Name cannot be empty.");
            return false;
        }
        if(product.getPrice() <= 0) {
            System.out.println("Price must be greater than 0.");
            return false;
        }
        // Call DAO
        return productDAO.addProduct(product);
    }

    @Override
    public ArrayList<Product> getAllProductsService() {
        return productDAO.getAllProducts();
    }
    @Override
    public Product getProductByIdService(int productId) {
        // We need to fetch the full list and find the product
        // For now, let's reuse getAllProducts() to keep it simple
        ArrayList<Product> list = productDAO.getAllProducts();
        for(Product p : list) {
            if(p.getProductId() == productId) {
                return p;
            }
        }
        return null; 
    }

    @Override
    public boolean updateProductService(Product product) {
        return productDAO.updateProduct(product);
    }

    @Override
    public boolean deleteProductService(int productId) {
        return productDAO.deleteProduct(productId);
    }
}