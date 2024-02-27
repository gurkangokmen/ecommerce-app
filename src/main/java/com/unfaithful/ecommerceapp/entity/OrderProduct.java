package com.unfaithful.ecommerceapp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "orderproduct")
@Data
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="order_id")
    private Long orderId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "price")
    private double price;

    @Column(name = "quantity")
    private int quantity;

    public OrderProduct() {
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", productId=" + productId +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
