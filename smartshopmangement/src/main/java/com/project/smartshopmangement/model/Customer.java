package com.project.smartshopmangement.model;

public class Customer {
    // to store customer Id
    private int customerId;
    // to store customer name
    private String name;
    // to store phone number (used for login)
    private String phone;
    // to store email address
    private String email;
    // to store the password
    private String password;

    /*-----------------------------------------------------*/
    // Default Constructor
    public Customer() {}

    /*-----------------------------------------------------*/
    // Parameterized Constructor
    public Customer(String name, String phone, String email, String password) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    /*-----------------------------------------------------*/
    // Getters and Setters
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    /*-----------------------------------------------------*/
    @Override
    public String toString() {
        return "Customer [ID : " + customerId 
                + "\n Name : " + name 
                + "\n Phone : " + phone 
                + "\n Email : " + email 
                + "\n Password : " + password 
                + "\n---------------------------------\n";
    }
}