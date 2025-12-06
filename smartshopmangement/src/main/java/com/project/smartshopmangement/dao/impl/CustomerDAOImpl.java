package com.project.smartshopmangement.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.project.smartshopmangement.dao.CustomerDAO;
import com.project.smartshopmangement.model.Customer;
import com.project.smartshopmangement.util.DataBaseUtil;

/*---------------------------------------------------
 * ------- Structure Of the Customer ---------
 * table : customers
 *+-------------+--------------+------+-----+---------+----------------+
| Field       | Type         | Null | Key | Default | Extra          |
+-------------+--------------+------+-----+---------+----------------+
| customer_id | int          | NO   | PRI | NULL    | auto_increment |
| name        | varchar(100) | YES  |     | NULL    |                |
| phone       | varchar(15)  | YES  | UNI | NULL    |                |
| email       | varchar(100) | YES  |     | NULL    |                |
| password    | varchar(50)  | NO   |     | NULL    |                |
+-------------+--------------+------+-----+---------+----------------+  */

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public int save(Customer customer) {
        int insertedRows = 0;
        try(Connection con = DataBaseUtil.establishConnection()) {
            
            // to create reference of preparedStatement interface
            // Note: We skip customer_id because it is Auto_Increment
            String sql = "INSERT INTO customers (name, phone, email, password) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            // setting query parameters
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getPhone());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getPassword());
            
            // to execute the query
            insertedRows = stmt.executeUpdate();
            
        } catch(SQLException sqe) {
            System.out.println("Error in Customer Save: " + sqe);
        }
        return insertedRows;
    }

    @Override
    public Customer findByPhoneAndPassword(String phone, String password) {
        Customer customer = null;
        try(Connection con = DataBaseUtil.establishConnection()) {
            
            // create statement
            String sqlQuery = "SELECT * FROM customers WHERE phone = ? AND password = ?";
            PreparedStatement stmt = con.prepareStatement(sqlQuery);
            stmt.setString(1, phone);
            stmt.setString(2, password);
            
            // to execute the select query
            ResultSet result = stmt.executeQuery();
            
            // fetching data
            if(result.next()) {
                customer = new Customer();
                // setting customer data from DB to Object
                customer.setCustomerId(result.getInt("customer_id"));
                customer.setName(result.getString("name"));
                customer.setPhone(result.getString("phone"));
                customer.setEmail(result.getString("email"));
                customer.setPassword(result.getString("password"));
            }
            
        } catch(SQLException sqe) {
            System.out.println("Error in Customer Login: " + sqe);
        }
        return customer;
    }
}