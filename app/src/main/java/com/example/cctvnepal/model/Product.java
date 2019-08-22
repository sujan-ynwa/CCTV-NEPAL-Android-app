package com.example.cctvnepal.model;

public class Product {


    private String productName;
    private String productPrice;
    private String specs;
    private String imagePath;
    private boolean available;
  //  private Categories categories;


    public Product() {
    }

    public Product(String productName, String productPrice, String specs, String imagePath, boolean available) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.specs = specs;
        this.imagePath = imagePath;
        this.available = available;
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
        this.specs = specs;
    }

    public String getImagepath() {
        return imagePath;
    }

    public void setImagepath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
