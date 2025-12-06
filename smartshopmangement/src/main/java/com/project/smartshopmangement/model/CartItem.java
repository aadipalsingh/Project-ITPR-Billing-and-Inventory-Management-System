package com.project.smartshopmangement.model;

public class CartItem {
    // to store unique item ID (Primary Key)
    private int itemId;
    // to link to the main Sale ID (Foreign Key)
    private int saleId;
    // to link which product was bought
    private int productId;
    // to store quantity bought
    private int quantity;
    // to store subtotal (Price * Quantity)
    private double subTotal;

    /*-----------------------------------------------------*/
    // Default Constructor
    public CartItem() {}

    /*-----------------------------------------------------*/
    // Parameterized Constructor
    public CartItem(int productId, int quantity, double subTotal) {
        this.productId = productId;
        this.quantity = quantity;
        this.subTotal = subTotal;
    }

    /*-----------------------------------------------------*/
    // Getters and Setters
    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public int getSaleId() { return saleId; }
    public void setSaleId(int saleId) { this.saleId = saleId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getSubTotal() { return subTotal; }
    public void setSubTotal(double subTotal) { this.subTotal = subTotal; }

    /*-----------------------------------------------------*/
    @Override
    public String toString() {
        return "Item ID : " + itemId 
                + "\n Product ID : " + productId 
                + "\n Quantity : " + quantity 
                + "\n SubTotal : " + subTotal 
                + "\n---------------------------------\n";
    }
}