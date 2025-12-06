package com.project.smartshopmangement.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.project.smartshopmangement.dao.AdminDAO;
import com.project.smartshopmangement.model.Admin;
import com.project.smartshopmangement.util.DataBaseUtil; 

/*--------------------------------------------------
 * ------- Structure Of the Users ---------
 * table : users
 * +----------+-------------+------+-----+---------+----------------+
| Field    | Type        | Null | Key | Default | Extra          |
+----------+-------------+------+-----+---------+----------------+
| user_id  | int         | NO   | PRI | NULL    | auto_increment |
| username | varchar(50) | NO   | UNI | NULL    |                |
| password | varchar(50) | NO   |     | NULL    |                |
| role     | varchar(20) | NO   |     | NULL    |                |
+----------+-------------+------+-----+---------+----------------+

 * */


public class AdminDAOImpl implements AdminDAO {

    /*-----------------------------------------------------*/
    @Override
    public Admin login(String username, String password) throws SQLException {
        
        // 1. Get Connection
        Connection con = DataBaseUtil.establishConnection();
        Admin user = null;
        
        // 2. Write SQL Query
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        
        // 3. Prepare Statement 
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        
        // 4. Execute Query
        ResultSet rs = pstmt.executeQuery();
        
        // 5. Check if user exists
        if (rs.next()) {
            user = new Admin();
            user.setUserId(rs.getInt("user_id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role"));
        }
        
      
        // but for now, we return the object.
        return user; 
    }
}