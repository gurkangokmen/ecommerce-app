package com.unfaithful.ecommerceapp.requestmodels;

import lombok.Data;

@Data
public class CustomerCart {
    private Long customerId;
    private Long productId;

    public CustomerCart(Long customerId, Long productId) {
        this.customerId = customerId;
        this.productId = productId;
    }
}
