package com.unfaithful.ecommerceapp.service;

import com.unfaithful.ecommerceapp.entity.Customer;

public interface CustomerService {
    Customer save(Customer theCustomer);

    Customer findByUsernameOrEmail(String username, String email);
}
