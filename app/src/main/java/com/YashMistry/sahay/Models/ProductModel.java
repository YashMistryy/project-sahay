package com.YashMistry.sahay.Models;

public class ProductModel {
    String name , image , price , desc , unit , pid , time , date , category ;

    public String getName() {
        return name;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }


    public ProductModel() {
    }

    public ProductModel(String name, String image, String price, String desc, String unit, String pid, String time, String date, String category) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.desc = desc;
        this.unit = unit;
        this.pid = pid;
        this.time = time;
        this.date = date;
        this.category = category;
    }
}
