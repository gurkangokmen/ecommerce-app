package com.unfaithful.ecommerceapp.controller;

import com.unfaithful.ecommerceapp.entity.Order;
import com.unfaithful.ecommerceapp.responsemodels.CustomerOrder;
import com.unfaithful.ecommerceapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderRestController {
    private OrderService orderService;

    @Autowired
    public OrderRestController(OrderService theOrderService) {
        this.orderService = theOrderService;
    }

    @PostMapping("/orders")
    public Order placeOrder(@RequestParam Long customerId){
        return this.orderService.placeOrder(customerId);
    }

    @GetMapping("/orders/{id}")
    public CustomerOrder GetOrderForCode(@PathVariable Long id){
        return this.orderService.GetOrderForCode(id);
    }

    @GetMapping("/orders")
    public List<CustomerOrder> GetAllOrdersForCustomer(@RequestParam Long customerId){
        System.out.println("is here!");
        return this.orderService.GetAllOrdersForCustomer(customerId);
    }
}
