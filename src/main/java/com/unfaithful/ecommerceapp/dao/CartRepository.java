package com.unfaithful.ecommerceapp.dao;

import com.unfaithful.ecommerceapp.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Long> {
    List<Cart> findByCustomerId(Long customerId);
    void deleteByCustomerId(Long customerId);
    Cart findByCustomerIdAndProductId(Long customerId, Long ProductId);

    @Query("select o from Cart o where o.customerId = :customer_id and o.productId != :product_id")
    List<Cart> findCartsExceptSpecificProductId(@Param("customer_id") Long customerId, @Param("product_id") Long ProductId);

    void deleteByCustomerIdAndProductId(Long customerId, Long ProductId);
}
