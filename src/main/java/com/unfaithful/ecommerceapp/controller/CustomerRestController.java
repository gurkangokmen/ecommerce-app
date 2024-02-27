package com.unfaithful.ecommerceapp.controller;

import com.unfaithful.ecommerceapp.entity.Customer;
import com.unfaithful.ecommerceapp.exception.CustomerAlreadyExistException;
import com.unfaithful.ecommerceapp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

    CustomerService customerService;

    @Autowired
    public CustomerRestController(CustomerService theCustomerService) {
        this.customerService = theCustomerService;
    }

    @PostMapping("/customers")
    public Customer addCustomer(@RequestBody Customer theCustomer) {
        if (customerService.findByUsernameOrEmail(theCustomer.getUsername(), theCustomer.getEmail()) == null) {
            Customer dbCustomer = customerService.save(theCustomer);

            return dbCustomer;
        }
        else {throw new CustomerAlreadyExistException("Customer already exist!");}
    }
}
