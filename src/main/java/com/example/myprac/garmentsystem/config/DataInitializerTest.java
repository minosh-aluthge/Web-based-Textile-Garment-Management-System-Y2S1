package com.example.myprac.garmentsystem.config;

import com.example.myprac.garmentsystem.order.model.Customer;
import com.example.myprac.garmentsystem.order.model.Product;
import com.example.myprac.garmentsystem.order.repositoty.CustomerRepository;
import com.example.myprac.garmentsystem.order.repositoty.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Simple test component to verify data initialization
 */
@Component
public class DataInitializerTest {

    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private ProductRepository productRepository;

    public void logDataStatus() {
        long customerCount = customerRepository.count();
        long productCount = productRepository.count();
        
        System.out.println("=== DATA INITIALIZATION STATUS ===");
        System.out.println("Customers loaded: " + customerCount);
        System.out.println("Products loaded: " + productCount);
        
        if (customerCount > 0) {
            Customer firstCustomer = customerRepository.findAll().get(0);
            System.out.println("First customer: " + firstCustomer.getName() + " (" + firstCustomer.getEmail() + ")");
        }
        
        if (productCount > 0) {
            Product firstProduct = productRepository.findAll().get(0);
            System.out.println("First product: " + firstProduct.getName() + " - $" + firstProduct.getStandardPrice());
        }
        
        System.out.println("=== END DATA STATUS ===");
    }
}
