package com.YashMistry.sahay.Models;

public class CartItemModel {
    String name , pid , desc , quantity , time , price  , image , category;

    public CartItemModel() {
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public CartItemModel(String name, String pid, String desc, String quantity, String time, String price, String image, String category) {
        this.name = name;
        this.pid = pid;
        this.image = image;
        this.category = category;
        this.desc = desc;
        this.quantity = quantity;
        this.time = time;
        this.price = price;
    }
}
