package com.project.smartshopmangement.dao;

import com.project.smartshopmangement.model.Customer;

public interface CustomerDAO {
    
    // method for inserting new customer data
    int save(Customer customer);
    
    // method to fetch a particular customer by phone and password (Login)
    Customer findByPhoneAndPassword(String phone, String password);

}