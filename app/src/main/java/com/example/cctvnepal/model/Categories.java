package com.example.cctvnepal.model;

import java.io.Serializable;

public class Categories implements Serializable {

    private String categoryName;
    private String imagePath;
    private String categoryCode;
    private String products;

    public Categories(String name, String image_url,String categoryCode,String products) {
        this.categoryName = name;
        this.imagePath = image_url;
        this.categoryCode = categoryCode;
        this.products = products;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getcategoryName() {
        return categoryName;
    }

    public void setcategoryName(String name) {
        this.categoryName = name;
    }

    public String getimagePath() {
        return imagePath;
    }

    public void setimagePath(String image_url) {
        this.imagePath = image_url;
    }
}
