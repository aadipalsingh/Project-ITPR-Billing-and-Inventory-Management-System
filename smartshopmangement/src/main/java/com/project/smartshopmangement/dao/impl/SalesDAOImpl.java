package com.project.smartshopmangement.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.project.smartshopmangement.dao.SalesDAO;
import com.project.smartshopmangement.model.CartItem;
import com.project.smartshopmangement.model.Sale;
import com.project.smartshopmangement.util.DataBaseUtil;

/*--------------------------------------------------
 * ------- Structure Of the sales ---------
 * table : sales
+--------------+----------+------+-----+-------------------+-------------------+
| Field        | Type     | Null | Key | Default           | Extra             |
+--------------+----------+------+-----+-------------------+-------------------+
| sale_id      | int      | NO   | PRI | NULL              | auto_increment    |
| sale_date    | datetime | YES  |     | CURRENT_TIMESTAMP | DEFAULT_GENERATED |
| total_amount | double   | YES  |     | NULL              |                   |
| customer_id  | int      | YES  | MUL | NULL              |                   |
+--------------+----------+------+-----+-------------------+-------------------+


 * */

public class SalesDAOImpl implements SalesDAO {

    @Override
    public boolean placeOrder(Sale sale, ArrayList<CartItem> cartItems) {
        Connection con = null;
        boolean isSuccess = false;
        
        try {
            con = DataBaseUtil.establishConnection();
            
            // ---------------------------------------------------------
            // 1. START TRANSACTION (Turn off Auto-Save)
            // ---------------------------------------------------------
            con.setAutoCommit(false);
            
            // ---------------------------------------------------------
            // 2. INSERT INTO SALES TABLE (The Bill Header)
            // ---------------------------------------------------------
            String sqlSale = "INSERT INTO sales (customer_id, total_amount, sale_date) VALUES (?, ?, ?)";
            
            // We need the GENERATED KEY (The Bill Number/Sale ID) back immediately
            PreparedStatement psSale = con.prepareStatement(sqlSale, Statement.RETURN_GENERATED_KEYS);
            psSale.setInt(1, sale.getCustomerId());
            psSale.setDouble(2, sale.getTotalAmount());
            psSale.setDate(3, sale.getSaleDate());
            
            int rows = psSale.executeUpdate();
            
            if (rows == 0) {
                throw new SQLException("Creating sale failed, no rows affected.");
            }

            // Get the generated Sale ID (e.g., 101)
            int generatedSaleId = 0;
            try (ResultSet generatedKeys = psSale.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedSaleId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating sale failed, no ID obtained.");
                }
            }

            // ---------------------------------------------------------
            // 3. INSERT ITEMS & UPDATE STOCK (The Loop)
            // ---------------------------------------------------------
            String sqlItem = "INSERT INTO cart_items (sale_id, product_id, qty, price_at_sale) VALUES (?, ?, ?, ?)";
            String sqlStock = "UPDATE products SET quantity = quantity - ? WHERE product_id = ?";
            
            PreparedStatement psItem = con.prepareStatement(sqlItem);
            PreparedStatement psStock = con.prepareStatement(sqlStock);
            
            for(CartItem item : cartItems) {
                // A. Add to Sale Items Table
                psItem.setInt(1, generatedSaleId); // Link to the Bill ID we just created
                psItem.setInt(2, item.getProductId());
                psItem.setInt(3, item.getQuantity());
                psItem.setDouble(4, item.getSubTotal());
                psItem.addBatch(); // Group them to send at once
                
                // B. Deduct Stock from Products Table
                psStock.setInt(1, item.getQuantity()); // Deduct this amount
                psStock.setInt(2, item.getProductId()); // From this product
                psStock.addBatch();
            }
            
            // Execute the batches
            psItem.executeBatch();
            psStock.executeBatch();
            
            // ---------------------------------------------------------
            // 4. COMMIT (Save Everything)
            // ---------------------------------------------------------
            con.commit();
            isSuccess = true;
            System.out.println("✅ Transaction Committed. Bill #" + generatedSaleId + " Generated.");

        } catch (SQLException e) {
            // ---------------------------------------------------------
            // 5. ROLLBACK (Undo Everything if Error)
            // ---------------------------------------------------------
            System.out.println("❌ Transaction Failed! Rolling back changes...");
            e.printStackTrace();
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // ---------------------------------------------------------
            // 6. CLEANUP (Reset AutoCommit and Close)
            // ---------------------------------------------------------
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return isSuccess;
    }

    @Override
    public ArrayList<Sale> getAllSales() {
        ArrayList<Sale> list = new ArrayList<>();
        try (Connection con = DataBaseUtil.establishConnection()) {
            String sql = "SELECT * FROM sales ORDER BY sale_id DESC"; // Latest first
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()) {
                Sale s = new Sale();
                s.setSaleId(rs.getInt("sale_id"));
                s.setCustomerId(rs.getInt("customer_id"));
                s.setTotalAmount(rs.getDouble("total_amount"));
                s.setSaleDate(rs.getDate("sale_date"));
                
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching sales: " + e.getMessage());
        }
        return list;
    }
}