package com.unfaithful.ecommerceapp.dao;

import com.unfaithful.ecommerceapp.entity.Order;
import com.unfaithful.ecommerceapp.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByCustomerId(Long customerId);
}
