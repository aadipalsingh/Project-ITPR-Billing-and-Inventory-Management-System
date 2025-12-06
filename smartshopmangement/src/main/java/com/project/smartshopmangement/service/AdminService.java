package com.project.smartshopmangement.service;

import com.project.smartshopmangement.model.Admin;

public interface AdminService {

    /*-----------------------------------------------------*/
    // Method to validate input and call DAO
     Admin loginService(String username, String password);
    
}