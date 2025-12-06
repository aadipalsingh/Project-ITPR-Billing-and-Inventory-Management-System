package com.project.smartshopmangement.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.project.smartshopmangement.dao.ProductDAO;
import com.project.smartshopmangement.model.Product;
import com.project.smartshopmangement.util.DataBaseUtil;

/*--------------------------------------------------
 * ------- Structure Of the Products ---------
 * table : products
+-------------+--------------+------+-----+---------+----------------+
| Field       | Type         | Null | Key | Default | Extra          |
+-------------+--------------+------+-----+---------+----------------+
| product_id  | int          | NO   | PRI | NULL    | auto_increment |
| name        | varchar(100) | NO   |     | NULL    |                |
| price       | double       | NO   |     | NULL    |                |
| quantity    | int          | YES  |     | 0       |                |
| category_id | int          | YES  | MUL | NULL    |                |
| brand_id    | int          | YES  | MUL | NULL    |                |
| tax_id      | int          | YES  | MUL | NULL    |                |
+-------------+--------------+------+-----+---------+----------------+

 * */


public class ProductDAOImpl implements ProductDAO {

    @Override
    public boolean addProduct(Product product) {
        int rows = 0;
        try (Connection con = DataBaseUtil.establishConnection()) {
            
            String sql = "INSERT INTO products (name, price, quantity, category_id, brand_id, tax_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            
            pstmt.setString(1, product.getName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.setInt(3, product.getQuantity());
            pstmt.setInt(4, product.getCategoryId());
            pstmt.setInt(5, product.getBrandId());
            pstmt.setInt(6, product.getTaxId());
            
            rows = pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("Error adding product: " + e);
        }
        return rows > 0;
    }

    @Override
    public ArrayList<Product> getAllProducts() {
        // defining an arraylist
        ArrayList<Product> productList = new ArrayList<>();
        
        try (Connection con = DataBaseUtil.establishConnection()) {
            
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM products";
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                p.setQuantity(rs.getInt("quantity"));
                p.setCategoryId(rs.getInt("category_id"));
                p.setBrandId(rs.getInt("brand_id"));
                p.setTaxId(rs.getInt("tax_id"));
                
                // inserting product into arraylist
                productList.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching products: " + e);
        }
        return productList;
    }
    
    @Override
    public boolean updateProduct(Product product) {
        try (Connection con = DataBaseUtil.establishConnection()) {
            String sql = "UPDATE products SET name=?, price=?, quantity=?, category_id=?, brand_id=?, tax_id=? WHERE product_id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, product.getName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.setInt(3, product.getQuantity());
            pstmt.setInt(4, product.getCategoryId());
            pstmt.setInt(5, product.getBrandId());
            pstmt.setInt(6, product.getTaxId());
            pstmt.setInt(7, product.getProductId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error Updating Product: " + e);
            return false;
        }
    }

    @Override
    public boolean deleteProduct(int productId) {
        try (Connection con = DataBaseUtil.establishConnection()) {
            String sql = "DELETE FROM products WHERE product_id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, productId);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error Deleting Product: " + e);
            return false;
        }
    }
}