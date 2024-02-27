package com.unfaithful.ecommerceapp.dao;

import com.unfaithful.ecommerceapp.entity.Cart;
import com.unfaithful.ecommerceapp.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct,Long> {

    List<OrderProduct> findByOrderId(Long orderId);
}
