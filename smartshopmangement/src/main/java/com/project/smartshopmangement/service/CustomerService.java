package com.project.smartshopmangement.service;

import com.project.smartshopmangement.model.Customer;

public interface CustomerService {

    // method to register new customer
    void registerCustomer(Customer customer);
    
    // method to login customer (returns object so Controller knows who logged in)
    Customer loginCustomer(String phone, String password);
    
}