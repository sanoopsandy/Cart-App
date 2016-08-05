package com.example.sanoop.cartapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanoop on 8/4/2016.
 */
public class CartResponse {
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("results")
    @Expose
    private List<Cart> results = new ArrayList<Cart>();

    public CartResponse() {
    }

    public CartResponse(Integer count, List<Cart> results) {
        this.count = count;
        this.results = results;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Cart> getResults() {
        return results;
    }

    public void setResults(List<Cart> results) {
        this.results = results;
    }
}
