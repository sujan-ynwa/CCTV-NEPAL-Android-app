package com.example.cctvnepal.model;

import java.io.Serializable;

public class Categories implements Serializable {

    private String categoryName;
    private String imagePath;

    public Categories(String name, String image_url) {
        this.categoryName = name;
        this.imagePath = image_url;
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
