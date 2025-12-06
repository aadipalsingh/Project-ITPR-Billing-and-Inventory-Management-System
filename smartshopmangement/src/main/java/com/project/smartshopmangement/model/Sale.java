package com.project.smartshopmangement.model;

import java.sql.Date; // SQL Date is better for Databases

public class Sale {
    // to store the unique Sale ID (Bill Number)
    private int saleId;
    // to link which customer bought this
    private int customerId;
    // to store total bill amount
    private double totalAmount;
    // to store the date of transaction
    private Date saleDate;

    /*-----------------------------------------------------*/
    // Default Constructor
    public Sale() {}

    /*-----------------------------------------------------*/
    // Parameterized Constructor
    public Sale(int customerId, double totalAmount, Date saleDate) {
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.saleDate = saleDate;
    }

    /*-----------------------------------------------------*/
    // Getters and Setters
    public int getSaleId() { return saleId; }
    public void setSaleId(int saleId) { this.saleId = saleId; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public Date getSaleDate() { return saleDate; }
    public void setSaleDate(Date saleDate) { this.saleDate = saleDate; }

    /*-----------------------------------------------------*/
    @Override
    public String toString() {
        return "Sale ID : " + saleId 
                + "\n Customer ID : " + customerId 
                + "\n Total Amount : " + totalAmount 
                + "\n Date : " + saleDate 
                + "\n---------------------------------\n";
    }

	 
}