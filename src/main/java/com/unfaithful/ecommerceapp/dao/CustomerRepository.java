package com.unfaithful.ecommerceapp.dao;

import com.unfaithful.ecommerceapp.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    // Query Creation Docs: https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
    Customer findByUsernameOrEmail(String username, String email);
}
