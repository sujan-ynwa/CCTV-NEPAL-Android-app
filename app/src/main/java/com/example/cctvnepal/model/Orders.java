package com.example.cctvnepal.model;

public class Orders {

    private String customerEmail;

    private String productName;

    private String companyName;

    private String contactNumber;

    private String contactName;

    private double price;

    private int quantity;

    private String purchaseDate;


    public Orders(){

    }

    public Orders(String customerEmail,String contactNumber,String contactName, String productName, String companyName, double price, int quantity, String purchaseDate) {
        this.customerEmail = customerEmail;
        this.productName = productName;
        this.companyName = companyName;
        this.price = price;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
