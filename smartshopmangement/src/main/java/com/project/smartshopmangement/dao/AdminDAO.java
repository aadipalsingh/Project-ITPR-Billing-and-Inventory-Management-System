package com.project.smartshopmangement.dao;

import java.sql.SQLException;

import com.project.smartshopmangement.model.Admin;

public interface AdminDAO {

    /*-----------------------------------------------------*/
    // Method to check if username and password match
    // Returns: The User object if valid, or null if invalid
     Admin login(String username, String password) throws SQLException;

}