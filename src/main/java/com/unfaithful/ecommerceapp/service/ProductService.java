package com.unfaithful.ecommerceapp.service;

import com.unfaithful.ecommerceapp.entity.Product;

public interface ProductService {
    Product getProduct(Long id);
    Product addProduct(Product theProduct);
    void deleteProduct(Long id);
    Product updateProduct(Product theProduct);
}
