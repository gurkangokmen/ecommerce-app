package com.unfaithful.ecommerceapp.dao;

import com.unfaithful.ecommerceapp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
