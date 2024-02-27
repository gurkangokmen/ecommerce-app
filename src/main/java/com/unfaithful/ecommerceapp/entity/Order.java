package com.unfaithful.ecommerceapp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "customerorder")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "order_date")
    private String orderDate;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "order_amount")
    private double orderAmount;


    public Order() {
    }

    public Order(Long customerId) {
        this.customerId = customerId;
    }


}
