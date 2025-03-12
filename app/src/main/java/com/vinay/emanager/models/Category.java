package com.vinay.emanager.models;

public class Category {

    private int categoryImage;
    private String categoryName;
    private int categoryColor;

    public int getCategoryColor() {
        return categoryColor;
    }

    public void setCategoryColor(int categoryColor) {
        this.categoryColor = categoryColor;
    }


    public Category() {
    }

    public Category(int categoryImage, String categoryName, int categoryColor) {
        this.categoryImage = categoryImage;
        this.categoryName = categoryName;
        this.categoryColor = categoryColor;
    }

    public int getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(int categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
