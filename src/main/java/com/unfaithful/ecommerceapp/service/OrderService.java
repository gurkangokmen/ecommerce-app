package com.unfaithful.ecommerceapp.service;

import com.unfaithful.ecommerceapp.entity.Order;
import com.unfaithful.ecommerceapp.responsemodels.CustomerOrder;

import java.util.List;

public interface OrderService {
    Order placeOrder(Long customerId);
    CustomerOrder GetOrderForCode(Long orderId);
    List<CustomerOrder> GetAllOrdersForCustomer(Long customerId);
}
