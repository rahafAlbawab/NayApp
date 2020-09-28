package com.RahafMaria.nay.Models;

public class CartModel {

    public int product_quantity ,product_id;
    public String product_name,product_image;
    public double product_price;

    public CartModel(int product_quantity, int product_id, String product_name, String product_image, double product_price) {
        this.product_quantity = product_quantity;
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_image = product_image;
        this.product_price = product_price;
    }
}
