package com.YashMistry.sahay.Models;

public class users {
    private String name;
    private String phone;
    private String pass;
    private String image;
    private String Address;

    public String getName() {
        return name;
    }

    public users(String name, String phone, String pass, String image, String address) {
        this.name = name;
        this.phone = phone;
        this.pass = pass;
        this.image = image;
        Address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddres() {
        return Address;
    }

    public void setAddres(String address) {
        Address = address;
    }

    public users() {
    }
}
