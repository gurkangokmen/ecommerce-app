package com.unfaithful.ecommerceapp.responsemodels;

import lombok.Data;

@Data
public class CustomerOrderProduct {
    private String productName;
    private double productPrice;
    private int quantity;

    public CustomerOrderProduct() {
    }

    public CustomerOrderProduct(String productName, double productPrice, int quantity) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }
}
