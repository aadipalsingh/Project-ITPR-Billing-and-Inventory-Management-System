package com.project.smartshopmangement.service.impl;

import com.project.smartshopmangement.dao.AdminDAO;
import com.project.smartshopmangement.dao.impl.AdminDAOImpl;
import com.project.smartshopmangement.model.Admin;
import com.project.smartshopmangement.service.AdminService;

public class AdminServiceImpl implements AdminService {

    private AdminDAO userDAO = new AdminDAOImpl();

    @Override
    public Admin loginService(String username, String password) {
        // Simple validation
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            System.out.println("❌ Username/Password cannot be empty.");
            return null;
        }
        
        // Call DAO 
        try {
            return userDAO.login(username, password);
        } catch (Exception e) {
            System.out.println("Login Error: " + e.getMessage());
            return null;
        }
    }
}