package com.unfaithful.ecommerceapp.service;

import com.unfaithful.ecommerceapp.dao.CartRepository;
import com.unfaithful.ecommerceapp.dao.CustomerRepository;
import com.unfaithful.ecommerceapp.dao.ProductRepository;
import com.unfaithful.ecommerceapp.entity.Cart;
import com.unfaithful.ecommerceapp.entity.Customer;
import com.unfaithful.ecommerceapp.entity.Product;
import com.unfaithful.ecommerceapp.requestmodels.CustomerCart;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{
    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public CartServiceImpl(CartRepository theCartRepository,ProductRepository theProductRepository,CustomerRepository theCustomerRepository) {
        this.cartRepository = theCartRepository;
        this.productRepository = theProductRepository;
        this.customerRepository = theCustomerRepository;
    }

    @Override
    public List<Cart> getCart(Long customerId) {
        return cartRepository.findByCustomerId(customerId);
    }

    @Override
    @Transactional
    public void emptyCart(Long customerId) {
        cartRepository.deleteByCustomerId(customerId);
    }

    @Override
    public Cart addProductToCart(CustomerCart theCustomerCart) {
        Optional<Product> dbProduct = productRepository.findById(theCustomerCart.getProductId());
        if (dbProduct.isPresent() == false){throw new RuntimeException("Product does not exist!");}

        Customer dbCurrentCustomer = customerRepository.findById(theCustomerCart.getCustomerId()).orElse(null);
        if (dbCurrentCustomer == null){throw new RuntimeException("Customer does not exist!");}

        Product dbCurrentProduct = dbProduct.get();
        List<Cart> dbCustomerCartsExceptCurrentProducts= cartRepository.findCartsExceptSpecificProductId(theCustomerCart.getCustomerId(), theCustomerCart.getProductId());
        Cart dbCustomerCartForCurrentProduct  = cartRepository.findByCustomerIdAndProductId(theCustomerCart.getCustomerId(), theCustomerCart.getProductId());


        double unitProductPrice = dbCurrentProduct.getProductPrice();

        List<Cart> myCarts = cartRepository.findByCustomerId(theCustomerCart.getCustomerId());

        // handle if product already exists in customer's cart AND there aren't any other products
        if (dbCustomerCartForCurrentProduct != null && dbCustomerCartsExceptCurrentProducts.size() == 0){

            cartRepository.saveAll(myCarts);

            dbCustomerCartForCurrentProduct.setTotalAmount(dbCustomerCartForCurrentProduct.getTotalAmount()+unitProductPrice);
            dbCustomerCartForCurrentProduct.setQuantity(dbCustomerCartForCurrentProduct.getQuantity()+1);
            dbCustomerCartForCurrentProduct.setTotalProductAmount(dbCustomerCartForCurrentProduct.getTotalProductAmount()+unitProductPrice);
            dbCustomerCartForCurrentProduct.setUpdatedDate(LocalDate.now().toString());

            return cartRepository.save(dbCustomerCartForCurrentProduct);
        }

        // handle if product already exists in customer's cart AND there are other products
        if (dbCustomerCartForCurrentProduct != null && dbCustomerCartsExceptCurrentProducts.size() > 0){

            for (int i = 0; i < dbCustomerCartsExceptCurrentProducts.size(); i++) {
                dbCustomerCartsExceptCurrentProducts.get(i).setTotalAmount(dbCustomerCartsExceptCurrentProducts.get(i).getTotalAmount()+unitProductPrice);
                dbCustomerCartsExceptCurrentProducts.get(i).setUpdatedDate(LocalDate.now().toString());
            }

            cartRepository.saveAll(dbCustomerCartsExceptCurrentProducts);

            dbCustomerCartForCurrentProduct.setTotalAmount(dbCustomerCartForCurrentProduct.getTotalAmount()+unitProductPrice);
            dbCustomerCartForCurrentProduct.setQuantity(dbCustomerCartForCurrentProduct.getQuantity()+1);
            dbCustomerCartForCurrentProduct.setTotalProductAmount(dbCustomerCartForCurrentProduct.getTotalProductAmount()+unitProductPrice);
            dbCustomerCartForCurrentProduct.setUpdatedDate(LocalDate.now().toString());

            return cartRepository.save(dbCustomerCartForCurrentProduct);
        }

        // handle if product does not exist in customer's cart AND there are other products
        if (dbCustomerCartForCurrentProduct == null && dbCustomerCartsExceptCurrentProducts.size() > 0){

            for (int i = 0; i < dbCustomerCartsExceptCurrentProducts.size(); i++) {
                dbCustomerCartsExceptCurrentProducts.get(i).setTotalAmount(dbCustomerCartsExceptCurrentProducts.get(i).getTotalAmount()+unitProductPrice);
                dbCustomerCartsExceptCurrentProducts.get(i).setUpdatedDate(LocalDate.now().toString());
            }

            cartRepository.saveAll(dbCustomerCartsExceptCurrentProducts);

            Cart cart = new Cart();
            cart.setCustomerId(theCustomerCart.getCustomerId());
            cart.setCreatedDate(LocalDate.now().toString());
            cart.setUpdatedDate(LocalDate.now().toString());
            cart.setTotalAmount(dbCustomerCartsExceptCurrentProducts.get(0).getTotalAmount());
            cart.setProductId(theCustomerCart.getProductId());
            cart.setQuantity(1);
            cart.setTotalProductAmount(unitProductPrice);

            return cartRepository.save(cart);
        }

        // handle if product does not exist in customer's cart AND there aren't other products
        if (dbCustomerCartForCurrentProduct == null && dbCustomerCartsExceptCurrentProducts.size() == 0){
            Cart cart = new Cart();
            cart.setCustomerId(theCustomerCart.getCustomerId());
            cart.setCreatedDate(LocalDate.now().toString());
            cart.setUpdatedDate(LocalDate.now().toString());
            cart.setTotalAmount(unitProductPrice);
            cart.setProductId(theCustomerCart.getProductId());
            cart.setQuantity(1);
            cart.setTotalProductAmount(unitProductPrice);

            return cartRepository.save(cart);
        }

        return null;




    }

    @Override
    @Transactional
    public void removeProductFromCart(Long customerId, Long productId) {
        Cart dbCustomerCartForCurrentProduct  = cartRepository.findByCustomerIdAndProductId(customerId, productId);
        if (dbCustomerCartForCurrentProduct == null){ throw new RuntimeException("Product or customer does not exists!");}


        List<Cart> dbCustomerCartsExceptCurrentProducts= cartRepository.findCartsExceptSpecificProductId(customerId, productId);
        // handle if product already exists in customer's cart AND there aren't any other products
        if (dbCustomerCartForCurrentProduct != null && dbCustomerCartsExceptCurrentProducts.size() == 0){
            cartRepository.deleteByCustomerIdAndProductId(customerId,productId);
        }

        // handle if product already exists in customer's cart AND there are other products
        if (dbCustomerCartForCurrentProduct != null && dbCustomerCartsExceptCurrentProducts.size() > 0){
            for (int i = 0; i < dbCustomerCartsExceptCurrentProducts.size(); i++) {
                dbCustomerCartsExceptCurrentProducts.get(i).setTotalAmount(dbCustomerCartsExceptCurrentProducts.get(i).getTotalAmount()-dbCustomerCartForCurrentProduct.getTotalProductAmount());
            }

            cartRepository.saveAll(dbCustomerCartsExceptCurrentProducts);
            cartRepository.deleteByCustomerIdAndProductId(customerId,productId);
        }


    }

    @Override
    @Transactional
    public Cart updateCart(Cart theCart) throws Exception {
        if (theCart.getQuantity()<0){
            throw new Exception("Quantity cannot less than 0!");
        }

        if (theCart.getQuantity() == 0){
            removeProductFromCart(theCart.getCustomerId(), theCart.getProductId());
            return null;
        }

        List<Cart> dbCustomerCartsExceptCurrentProducts= cartRepository.findCartsExceptSpecificProductId(theCart.getCustomerId(), theCart.getProductId());
        Product tempProduct = productRepository.findById(theCart.getProductId()).orElse(null);
        Customer tempCustomer = customerRepository.findById(theCart.getCustomerId()).orElse(null);
        // handle there aren't any other products
        if (theCart.getQuantity() > 0 && dbCustomerCartsExceptCurrentProducts.size() == 0 && tempProduct !=null && tempCustomer !=null){
            Product dbCurrentProduct = productRepository.findById(theCart.getProductId()).get();
            double unitProductPrice = dbCurrentProduct.getProductPrice();

            double newTotalAmount = theCart.getTotalAmount() - theCart.getTotalProductAmount() + (unitProductPrice* theCart.getQuantity());

            theCart.setTotalAmount(newTotalAmount);
            theCart.setTotalProductAmount(unitProductPrice* theCart.getQuantity());
            theCart.setUpdatedDate(LocalDate.now().toString());
            System.out.println("handle1");
            return cartRepository.save(theCart);
        }

        // handle there are other products
        if (theCart.getQuantity() > 0 && dbCustomerCartsExceptCurrentProducts.size() > 0 && tempProduct !=null && tempCustomer !=null){
            Product dbCurrentProduct = productRepository.findById(theCart.getProductId()).get();
            double unitProductPrice = dbCurrentProduct.getProductPrice();

            double newTotalAmount = theCart.getTotalAmount() - theCart.getTotalProductAmount() + (unitProductPrice* theCart.getQuantity());

            for (int i = 0; i < dbCustomerCartsExceptCurrentProducts.size(); i++) {
                dbCustomerCartsExceptCurrentProducts.get(i).setTotalAmount(newTotalAmount);
            }

            cartRepository.saveAll(dbCustomerCartsExceptCurrentProducts);

            theCart.setTotalAmount(newTotalAmount);
            theCart.setTotalProductAmount(unitProductPrice* theCart.getQuantity());
            theCart.setUpdatedDate(LocalDate.now().toString());
            System.out.println("handle2");
            return cartRepository.save(theCart);
        }

        return null;







    }
}
