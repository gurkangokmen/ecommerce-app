package com.unfaithful.ecommerceapp.controller;

import com.unfaithful.ecommerceapp.entity.Cart;
import com.unfaithful.ecommerceapp.requestmodels.CustomerCart;
import com.unfaithful.ecommerceapp.requestmodels.UpdateCustomerCart;
import com.unfaithful.ecommerceapp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CartRestController {
    private CartService cartService;

    @Autowired
    public CartRestController(CartService theCartService) {
        this.cartService = theCartService;
    }

    @GetMapping("/carts/{id}")
    List<Cart> getCart(@PathVariable Long id){
        return cartService.getCart(id);
    }

    @DeleteMapping("/carts/{id}")
    void emptyCart(@PathVariable Long id){
        cartService.emptyCart(id);
    }

    @PostMapping("/carts")
    Cart addProductToCart(@RequestBody CustomerCart customerCart){
        return cartService.addProductToCart(customerCart);
    }

    @DeleteMapping("/carts")
    void removeProductFromCart(@RequestParam Long customerId, @RequestParam Long productId){
        cartService.removeProductFromCart(customerId,productId);
    }

    @PutMapping("/carts")
    Cart updateCart(@RequestBody Cart theCart) throws Exception {
        return cartService.updateCart(theCart);
    }
}
