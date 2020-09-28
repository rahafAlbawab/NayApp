package com.RahafMaria.nay.Models;

public class LipsModel {
    public String  product_image;
    public String product_name;
    public double product_price;
    public int product_id;


    public LipsModel(String product_image, String product_name, double product_price, int product_id) {
        this.product_image = product_image;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_id = product_id;
    }
}
