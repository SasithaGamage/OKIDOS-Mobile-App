package com.store.okidosmobileapp;

public class CartList
{
    private String cartItemID;
    private String cartProductID;
    private String cartItemName;
    private String cartItemDate;
    private String cartItemTime;
    private String cartItemCategory;
    private String cartItemPrice;
    private String cartItemQuantity;

    public CartList() {
    }

    public CartList(String cartItemID, String cartProductID, String cartItemName, String cartItemDate, String cartItemTime, String cartItemCategory, String cartItemPrice, String cartItemQuantity) {
        this.cartItemID = cartItemID;
        this.cartProductID = cartProductID;
        this.cartItemName = cartItemName;
        this.cartItemDate = cartItemDate;
        this.cartItemTime = cartItemTime;
        this.cartItemCategory = cartItemCategory;
        this.cartItemPrice = cartItemPrice;
        this.cartItemQuantity = cartItemQuantity;
    }

    public String getCartItemID() {
        return cartItemID;
    }

    public void setCartItemID(String cartItemID) {
        this.cartItemID = cartItemID;
    }

    public String getCartProductID() {
        return cartProductID;
    }

    public void setCartProductID(String cartProductID) {
        this.cartProductID = cartProductID;
    }

    public String getCartItemName() {
        return cartItemName;
    }

    public void setCartItemName(String cartItemName) {
        this.cartItemName = cartItemName;
    }

    public String getCartItemDate() {
        return cartItemDate;
    }

    public void setCartItemDate(String cartItemDate) {
        this.cartItemDate = cartItemDate;
    }

    public String getCartItemTime() {
        return cartItemTime;
    }

    public void setCartItemTime(String cartItemTime) {
        this.cartItemTime = cartItemTime;
    }

    public String getCartItemCategory() {
        return cartItemCategory;
    }

    public void setCartItemCategory(String cartItemCategory) {
        this.cartItemCategory = cartItemCategory;
    }

    public String getCartItemPrice() {
        return cartItemPrice;
    }

    public void setCartItemPrice(String cartItemPrice) {
        this.cartItemPrice = cartItemPrice;
    }

    public String getCartItemQuantity() {
        return cartItemQuantity;
    }

    public void setCartItemQuantity(String cartItemQuantity) {
        this.cartItemQuantity = cartItemQuantity;
    }

    @Override
    public String toString() {
        return "CartList{" +
                "cartItemID='" + cartItemID + '\'' +
                ", cartProductID='" + cartProductID + '\'' +
                ", cartItemName='" + cartItemName + '\'' +
                ", cartItemDate='" + cartItemDate + '\'' +
                ", cartItemTime='" + cartItemTime + '\'' +
                ", cartItemCategory='" + cartItemCategory + '\'' +
                ", cartItemPrice='" + cartItemPrice + '\'' +
                ", cartItemQuantity='" + cartItemQuantity + '\'' +
                '}';
    }
}
