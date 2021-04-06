package com.store.okidosmobileapp;

public class Orders {
    private String ID;
    private String quantity;
    private String date;
    private String time;
    private String cusName;
    private String cusPhone;
    private String address;
    private String cusEmail;

    public Orders() {
    }

    public Orders(String ID, String quantity, String cusName, String cusPhone, String address, String cusEmail) {
        this.ID = ID;
        this.quantity = quantity;
        this.cusName = cusName;
        this.cusPhone = cusPhone;
        this.address = address;
        this.cusEmail = cusEmail;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getID() {
        return ID;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getCusName() {
        return cusName;
    }

    public String getCusPhone() {
        return cusPhone;
    }

    public String getAddress() {
        return address;
    }

    public String getCusEmail() {
        return cusEmail;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public void setCusPhone(String cusPhone) {
        this.cusPhone = cusPhone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCusEmail(String cusEmail) {
        this.cusEmail = cusEmail;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "ID='" + ID + '\'' +
                ", quantity='" + quantity + '\'' +
                ", cusName='" + cusName + '\'' +
                ", cusPhone='" + cusPhone + '\'' +
                ", address='" + address + '\'' +
                ", cusEmail='" + cusEmail + '\'' +
                '}';
    }
}
