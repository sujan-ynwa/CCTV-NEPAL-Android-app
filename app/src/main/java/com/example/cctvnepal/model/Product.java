package com.example.cctvnepal.model;

public class Product {


    // these names should be exactly same as the name on the api
    private String productName;
    private double price;
    private String specs;
    private String imagePath;
    private boolean available;
    private String companyName;
    private String warranty;
  //  private Categories categories;


    public Product() {
    }

    public Product(String productName,String specs, String imagePath,String companyName,String warranty,double price, boolean available) {
        this.productName = productName;
        this.price = price;
        this.companyName = companyName;
        this.warranty = warranty;
        this.specs = specs;
        this.imagePath = imagePath;
        this.available = available;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
