package com.project.smartshopmangement.service.impl;

import com.project.smartshopmangement.dao.CustomerDAO;
import com.project.smartshopmangement.dao.impl.CustomerDAOImpl;
import com.project.smartshopmangement.model.Customer;
import com.project.smartshopmangement.service.CustomerService;

public class CustomerServiceImpl implements CustomerService {

    private CustomerDAO customerDAO;

    // constructor 
    public CustomerServiceImpl() {
        // to initialize DAO reference
        customerDAO = new CustomerDAOImpl();
    }

    @Override
    public void registerCustomer(Customer customer) {
        
        if(customer == null) // verifying customer object null
        {
            System.out.println("Customer data is empty");
        }
        else if(customer.getName().isEmpty())
        {
            System.out.println("Name cannot be empty");
        }
        else if(customer.getPhone().length() != 10)
        {
            System.out.println("Phone number must be exactly 10 digits");
        }
        else if(customer.getPassword().length() < 4)
        {
            System.out.println("Password is too weak (min 4 chars)");
        }
        else
        {
            // calling save method() of dao to insert the customer data
            int rows = customerDAO.save(customer);
            
            if(rows > 0) // number of rows greater than 0 indicates successful insert
            {
                System.out.println("✅ Customer Successfully Registered!");
            }
            else
            {
                System.out.println("❌ Unable to register customer (Phone might already exist)");
            }
        }
    }

    @Override
    public Customer loginCustomer(String phone, String password) {
        // Validation for empty inputs
        if(phone.isEmpty() || password.isEmpty()) {
            System.out.println("❌ Phone or Password cannot be empty");
            return null;
        }
        
        // Call DAO
        return customerDAO.findByPhoneAndPassword(phone, password);
    }
}