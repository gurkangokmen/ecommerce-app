package com.unfaithful.ecommerceapp.service;

import com.unfaithful.ecommerceapp.dao.*;
import com.unfaithful.ecommerceapp.entity.Cart;
import com.unfaithful.ecommerceapp.entity.Order;
import com.unfaithful.ecommerceapp.entity.OrderProduct;
import com.unfaithful.ecommerceapp.entity.Product;
import com.unfaithful.ecommerceapp.responsemodels.CustomerOrder;
import com.unfaithful.ecommerceapp.responsemodels.CustomerOrderProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{
    private OrderRepository orderRepository;
    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private CustomerRepository customerRepository;
    private OrderProductRepository orderProductRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository theOrderRepository, CartRepository theCartRepository, ProductRepository theProductRepository,CustomerRepository theCustomerRepository, OrderProductRepository theOrderProductRepository) {
        this.orderRepository = theOrderRepository;
        this.cartRepository = theCartRepository;
        this.productRepository = theProductRepository;
        this.customerRepository = theCustomerRepository;
        this.orderProductRepository = theOrderProductRepository;
    }

    @Override
    @Transactional
    public Order placeOrder(Long customerId) {
        List<OrderProduct> orderProducts = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        List<Cart> myCarts = cartRepository.findByCustomerId(customerId);

        // System.out.println(myCarts); ===> CORRECT

        if (myCarts.size() == 0){
            throw new RuntimeException("Your cart is empty!");
        }

        if (myCarts.size()>0){

            Order currentOrder = new Order();
            currentOrder.setCustomerId(customerId);

            currentOrder.setOrderDate(LocalDate.now().toString());
            currentOrder.setPaymentMethod("card");
            currentOrder.setShippingAddress(customerRepository.findById(customerId).get().getAddress());
            currentOrder.setOrderStatus("getting ready");

            currentOrder.setOrderAmount(myCarts.get(0).getTotalAmount());





            for (int i = 0; i < myCarts.size(); i++) {
                Optional<Product> dbProduct = productRepository.findById(myCarts.get(i).getProductId());
                Boolean validateProduct = dbProduct.isPresent();
                if (validateProduct){
                    //check stock size for product
                    if (myCarts.get(i).getQuantity()<=dbProduct.get().getProductStock()){
                        dbProduct.get().setProductStock(dbProduct.get().getProductStock()-myCarts.get(i).getQuantity());
                        products.add(dbProduct.get());


                        OrderProduct tempOrderProduct = new OrderProduct();

                        tempOrderProduct.setProductId(myCarts.get(i).getProductId());
                        tempOrderProduct.setPrice(myCarts.get(i).getTotalProductAmount());
                        tempOrderProduct.setQuantity(myCarts.get(i).getQuantity());
                        orderProducts.add(tempOrderProduct);
                    }

                    else {
                        throw new RuntimeException("Sold out!");
                    }
                }


            }


            // update product stock
            productRepository.saveAll(products);

            Order savedOrder = orderRepository.save(currentOrder);

            for (int i = 0; i < orderProducts.size(); i++) {
                orderProducts.get(i).setOrderId(savedOrder.getId());
            }

            orderProductRepository.saveAll(orderProducts);

            // empty cart
            cartRepository.deleteByCustomerId(customerId);


            return savedOrder;
        }

        return null;
    }

    @Override
    public CustomerOrder GetOrderForCode(Long orderId) {
        Order dbOrder = orderRepository.findById(orderId).orElse(null);
        System.out.println(dbOrder);
        if (dbOrder!=null){
            CustomerOrder customerOrder = new CustomerOrder();
            customerOrder.setOrderDate(dbOrder.getOrderDate());
            customerOrder.setShippingAddress(dbOrder.getShippingAddress());
            customerOrder.setOrderStatus(dbOrder.getOrderStatus());
            customerOrder.setOrderAmount(dbOrder.getOrderAmount());

            List<OrderProduct> orderProducts = orderProductRepository.findByOrderId(orderId);
            List<CustomerOrderProduct> customerProducts = new ArrayList<>();
            for (int i = 0; i < orderProducts.size(); i++) {
                CustomerOrderProduct customerOrderProduct = new CustomerOrderProduct();
                customerOrderProduct.setProductPrice(orderProducts.get(i).getPrice());
                customerOrderProduct.setProductName(productRepository.findById(orderProducts.get(i).getProductId()).get().getProductName());
                customerOrderProduct.setQuantity(orderProducts.get(i).getQuantity());
                customerProducts.add(customerOrderProduct);

            }
            customerOrder.setCustomerProducts(customerProducts);

            System.out.println(customerOrder);

            return customerOrder;
        }
        return null;
    }

    @Override
    public List<CustomerOrder> GetAllOrdersForCustomer(Long customerId) {
        List<Order> dbOrders = orderRepository.findByCustomerId(customerId);

        if (dbOrders.size()<=0){return null;}
        if (dbOrders.size()>0){
            List<CustomerOrder> returnOrders = new ArrayList<>();

            for (int i = 0; i < dbOrders.size(); i++) {
                returnOrders.add(GetOrderForCode(dbOrders.get(i).getId()));
            }

            return returnOrders;
        }

        return null;
    }
}
