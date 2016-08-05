package com.example.sanoop.cartapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sanoop on 8/4/2016.
 */
public class Item implements Serializable{
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("cart")
    @Expose
    private Integer cart;
    @SerializedName("addedTime")
    @Expose
    private String addedTime;
    @SerializedName("image")
    @Expose
    private Object image;
    @SerializedName("barcode")
    @Expose
    private String barcode;

    public Item(){

    }

    public Item(Integer id, String name, Integer price, Integer quantity, Integer cart, String addedTime, Object image, String barcode, Boolean purchase, Object thumbnail) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.cart = cart;
        this.addedTime = addedTime;
        this.image = image;
        this.barcode = barcode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getCart() {
        return cart;
    }

    public void setCart(Integer cart) {
        this.cart = cart;
    }

    public String getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(String addedTime) {
        this.addedTime = addedTime;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
