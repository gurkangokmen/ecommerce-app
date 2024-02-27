package com.unfaithful.ecommerceapp.requestmodels;

import lombok.Data;

@Data
public class UpdateCustomerCart {

    private Long customerId;
    private Long productId;
    private int quantity;

    public UpdateCustomerCart(Long customerId, Long productId, int quantity) {
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
    }
}
