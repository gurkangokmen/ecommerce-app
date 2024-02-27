package com.unfaithful.ecommerceapp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cart")
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "created_at")
    private String createdDate;

    @Column(name = "updated_at")
    private String updatedDate;

    @Column(name = "total_amount")
    private double totalAmount;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "total_product_amount")
    private double totalProductAmount;

    public Cart() {
    }

    public Cart(Long id, Long customerId, String createdDate, String updatedDate, double totalAmount, Long productId, int quantity, double totalProductAmount) {
        this.id = id;
        this.customerId = customerId;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.totalAmount = totalAmount;
        this.productId = productId;
        this.quantity = quantity;
        this.totalProductAmount = totalProductAmount;
    }
}
