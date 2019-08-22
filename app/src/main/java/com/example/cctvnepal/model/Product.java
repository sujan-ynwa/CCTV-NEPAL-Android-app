package com.example.cctvnepal.model;

public class Product {


    private String productName;
    private String productPrice;
    private String specs;

    public Product() {
    }

    public Product(String productName, String productPrice, String specs) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.specs = specs;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        specs = specs;
    }
}
