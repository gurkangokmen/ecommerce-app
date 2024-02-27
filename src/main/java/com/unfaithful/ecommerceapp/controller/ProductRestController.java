package com.unfaithful.ecommerceapp.controller;

import com.unfaithful.ecommerceapp.entity.Product;
import com.unfaithful.ecommerceapp.exception.ProductNotFoundException;
import com.unfaithful.ecommerceapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductRestController {
    private ProductService productService;

    @Autowired
    public ProductRestController(ProductService theProductService) {
        this.productService = theProductService;
    }

    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable Long id){
        Product returnedProduct = productService.getProduct(id);

        if (returnedProduct == null){
            throw new ProductNotFoundException("Product not found!");
        }

        return returnedProduct;

    }

    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product){
        return productService.addProduct(product);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }

    @PutMapping("/products")
    public Product updateProduct(@RequestBody Product product){
        return productService.updateProduct(product);
    }
}
