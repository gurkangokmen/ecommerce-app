package com.unfaithful.ecommerceapp.service;

import com.unfaithful.ecommerceapp.entity.Cart;
import com.unfaithful.ecommerceapp.requestmodels.CustomerCart;
import com.unfaithful.ecommerceapp.requestmodels.UpdateCustomerCart;

import java.util.List;

public interface CartService {
    List<Cart> getCart(Long customerId);
    void emptyCart(Long customerId);
    Cart addProductToCart(CustomerCart theCustomerCart);
    void removeProductFromCart(Long customerId,Long productId);
    Cart updateCart(Cart theCart) throws Exception;
}
