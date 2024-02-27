package com.unfaithful.ecommerceapp;

import com.unfaithful.ecommerceapp.dao.OrderRepository;
import com.unfaithful.ecommerceapp.entity.Order;
import com.unfaithful.ecommerceapp.entity.OrderProduct;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class ECommerceProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceProjectApplication.class, args);
	}





}
