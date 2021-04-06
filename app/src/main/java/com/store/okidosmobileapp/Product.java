package com.store.okidosmobileapp;

import java.util.Arrays;

public class Product
{

    private String productID;
    private String productName;
    private String Date;
    private String Time;
    private String productDescription;
    private String productGender;
    private String productCategory;
    private String productPrice;
    private String productImage;

    public Product() {
    }

    public Product(String productID, String productName, String date, String time, String productDescription, String productGender, String productCategory, String productPrice, String productImage) {
        this.productID = productID;
        this.productName = productName;
        this.Date = date;
        this.Time = time;
        this.productDescription = productDescription;
        this.productGender = productGender;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.productImage = productImage;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductGender() {
        return productGender;
    }

    public void setProductGender(String productGender) {
        this.productGender = productGender;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }


    @Override
    public String toString() {
        return "Product{" +
                "productID='" + productID + '\'' +
                ", productName='" + productName + '\'' +
                ", Date='" + Date + '\'' +
                ", Time='" + Time + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", productGender='" + productGender + '\'' +
                ", productCategory='" + productCategory + '\'' +
                ", productPrice='" + productPrice + '\'' +
                ", productImage='" + productImage + '\'' +
                '}';
    }
}
