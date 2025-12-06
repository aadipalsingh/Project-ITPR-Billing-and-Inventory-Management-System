package com.project.smartshopmangement.model;

public class Product {
    // to store product Id
    private int productId;
    // to store product name
    private String name;
    // to store product price
    private double price;
    // to store available quantity
    private int quantity;
    
    // Foreign Keys (Linking to other tables)
    private int categoryId;
    private int brandId;
    private int taxId;

    /*-----------------------------------------------------*/
    // Default Constructor
    public Product() {}

    /*-----------------------------------------------------*/
    // Parameterized Constructor
    public Product(String name, double price, int quantity, int categoryId, int brandId, int taxId) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.categoryId = categoryId;
        this.brandId = brandId;
        this.taxId = taxId;
    }

    /*-----------------------------------------------------*/
    // Getters and Setters
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public int getBrandId() { return brandId; }
    public void setBrandId(int brandId) { this.brandId = brandId; }

    public int getTaxId() { return taxId; }
    public void setTaxId(int taxId) { this.taxId = taxId; }

    /*-----------------------------------------------------*/
    @Override
    public String toString() {
        return "Product ID : " + productId 
                + "\n Name : " + name 
                + "\n Price : " + price 
                + "\n Quantity : " + quantity 
                + "\n CategoryID : " + categoryId 
                + "\n BrandID : " + brandId 
                + "\n TaxID : " + taxId 
                + "\n---------------------------------\n";
    }
}