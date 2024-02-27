package com.unfaithful.ecommerceapp.service;

import com.unfaithful.ecommerceapp.dao.CustomerRepository;
import com.unfaithful.ecommerceapp.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService{

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository theCustomerRepository) {
        this.customerRepository = theCustomerRepository;
    }

    @Override
    public Customer save(Customer theCustomer) {
        return customerRepository.save(theCustomer);
    }

    @Override
    public Customer findByUsernameOrEmail(String username, String email) {
        return customerRepository.findByUsernameOrEmail(username,email);
    }
}
