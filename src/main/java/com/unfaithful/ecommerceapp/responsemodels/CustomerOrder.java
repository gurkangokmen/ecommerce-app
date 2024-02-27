package com.unfaithful.ecommerceapp.responsemodels;

import lombok.Data;

import java.util.List;

@Data
public class CustomerOrder {
    private String orderDate;
    private String shippingAddress;
    private String orderStatus;
    private double orderAmount;
    private List<CustomerOrderProduct> customerProducts;

    public CustomerOrder() {
    }

    public CustomerOrder(String orderDate, String shippingAddress, String orderStatus, double orderAmount, List<CustomerOrderProduct> customerProducts) {
        this.orderDate = orderDate;
        this.shippingAddress = shippingAddress;
        this.orderStatus = orderStatus;
        this.orderAmount = orderAmount;
        this.customerProducts = customerProducts;
    }

    public void add(CustomerOrderProduct customerOrderProduct){
        customerProducts.add(customerOrderProduct);
    }

    @Override
    public String toString() {
        return "CustomerOrder{" +
                "orderDate='" + orderDate + '\'' +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderAmount=" + orderAmount +
                ", customerProducts=" + customerProducts +
                '}';
    }
}
