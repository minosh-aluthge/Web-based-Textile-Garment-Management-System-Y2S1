package com.example.myprac.garmentsystem.order.controller;

import com.example.myprac.garmentsystem.order.model.Customer;
import com.example.myprac.garmentsystem.order.model.Order;
import com.example.myprac.garmentsystem.order.model.OrderRequest;
import com.example.myprac.garmentsystem.order.model.Product;
import com.example.myprac.garmentsystem.order.service.SalesOrderService;
import com.example.myprac.garmentsystem.order.dto.PaymentRequest;
import com.example.myprac.garmentsystem.order.dto.PaymentResponse;
import com.example.myprac.garmentsystem.order.strategy.PaymentProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/sales")
@CrossOrigin(origins = "*")
public class SalesOrderController {

    @Autowired
    private SalesOrderService salesOrderService;

    @Autowired
    private PaymentProcessor paymentProcessor;

    // ========== ORDER MANAGEMENT ==========
    
    // CREATE Order (C in CRUD)
    @PostMapping("/orders")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest) {
        try {
            Order order = new Order();
            order.setSubject(orderRequest.getSubject());
            order.setDescription(orderRequest.getDescription());
            order.setDeliveryDate(orderRequest.getDeliveryDate());
            order.setCreatedBy(orderRequest.getCreatedBy());
            
            Customer customer = new Customer();
            customer.setId(orderRequest.getCustomerId());
            order.setCustomer(customer);
            
            Order createdOrder = salesOrderService.createOrder(order, orderRequest.getOrderItems());
            return ResponseEntity.ok(createdOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error creating order: " + e.getMessage());
        }
    }

    // READ Orders: list all (R in CRUD)
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = salesOrderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // READ Order by ID (R in CRUD)
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        Optional<Order> order = salesOrderService.getOrderById(id);
        if (order.isPresent()) {
            return ResponseEntity.ok(order.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // READ Orders by status (R in CRUD - filtered)
    @GetMapping("/orders/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable String status) {
        List<Order> orders = salesOrderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }

    // READ Orders by search term (R in CRUD - search)
    @GetMapping("/orders/search")
    public ResponseEntity<List<Order>> searchOrders(@RequestParam String q) {
        List<Order> orders = salesOrderService.searchOrders(q);
        return ResponseEntity.ok(orders);
    }

    // UPDATE Order (U in CRUD)
    @PutMapping("/orders/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        try {
            Order updatedOrder = salesOrderService.updateOrder(id, order);
            return ResponseEntity.ok(updatedOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error updating order: " + e.getMessage());
        }
    }

    // UPDATE Order production stage (U in CRUD - domain specific)
    @PutMapping("/orders/{id}/stage")
    public ResponseEntity<?> updateOrderStage(
            @PathVariable Long id,
            @RequestBody Map<String, Object> payload
    ) {
        try {
            String stage = (String) payload.getOrDefault("stage", null);
            Integer index = null;
            Object idxObj = payload.get("index");
            if (idxObj instanceof Number) {
                index = ((Number) idxObj).intValue();
            } else if (idxObj instanceof String) {
                index = Integer.parseInt((String) idxObj);
            }

            if (stage == null || index == null) {
                return ResponseEntity.badRequest().body("stage and index are required");
            }

            // Serialize optional details object to JSON
            String detailsJson = null;
            Object details = payload.get("details");
            if (details != null) {
                ObjectMapper mapper = new ObjectMapper();
                detailsJson = mapper.writeValueAsString(details);
            }

            Order updated = salesOrderService.updateOrderStage(id, stage, index, detailsJson);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error updating stage: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error serializing details: " + e.getMessage());
        }
    }

    // DELETE Order (D in CRUD)
    @DeleteMapping("/orders/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        try {
            salesOrderService.deleteOrder(id);
            return ResponseEntity.ok("Order deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error deleting order: " + e.getMessage());
        }
    }

    // ========== CUSTOMER MANAGEMENT ==========
    
    // CREATE Customer (C in CRUD)
    @PostMapping("/customers")
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        try {
            Customer createdCustomer = salesOrderService.createCustomer(customer);
            return ResponseEntity.ok(createdCustomer);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error creating customer: " + e.getMessage());
        }
    }

    // READ Customers: list all (R in CRUD)
    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = salesOrderService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    // READ Customer by ID (R in CRUD)
    @GetMapping("/customers/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = salesOrderService.getCustomerById(id);
        if (customer.isPresent()) {
            return ResponseEntity.ok(customer.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // UPDATE Customer (U in CRUD)
    @PutMapping("/customers/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        try {
            Customer updatedCustomer = salesOrderService.updateCustomer(id, customer);
            return ResponseEntity.ok(updatedCustomer);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error updating customer: " + e.getMessage());
        }
    }

    // DELETE Customer (D in CRUD - soft delete in service)
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        try {
            salesOrderService.deleteCustomer(id);
            return ResponseEntity.ok("Customer deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error deleting customer: " + e.getMessage());
        }
    }

    // ========== PRODUCT MANAGEMENT ==========
    
    // READ Products: list all (R in CRUD)
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = salesOrderService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // READ Product by ID (R in CRUD)
    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Optional<Product> product = salesOrderService.getProductById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // READ Products by category (R in CRUD - filtered)
    @GetMapping("/products/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        List<Product> products = salesOrderService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    // CREATE Product (C in CRUD)
    @PostMapping("/products")
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        try {
            Product createdProduct = salesOrderService.createProduct(product);
            return ResponseEntity.ok(createdProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error creating product: " + e.getMessage());
        }
    }

    // UPDATE Product (U in CRUD)
    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        try {
            Product updatedProduct = salesOrderService.updateProduct(id, product);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error updating product: " + e.getMessage());
        }
    }

    // DELETE Product (D in CRUD)
    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            salesOrderService.deleteProduct(id);
            return ResponseEntity.ok("Product deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error deleting product: " + e.getMessage());
        }
    }

    // ========== PAYMENT PROCESSING (Strategy Pattern) ==========
    @PostMapping("/payments")
    public ResponseEntity<?> processPayment(@RequestBody PaymentRequest request) {
        try {
            PaymentResponse response = paymentProcessor.process(request);
            if (request.getOrderId() != null) {
                salesOrderService.recordPayment(request.getOrderId(), response.isSuccess(), response.getTransactionId(), response.getMessage());
            }
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body("Payment error: " + ex.getMessage());
        }
    }

    // ========== TEST ENDPOINTS ==========
    
    @GetMapping("/test")
    public ResponseEntity<?> testConnection() {
        return ResponseEntity.ok("Backend is running! Frontend-Backend connection successful!");
    }
    
    @GetMapping("/health")
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "timestamp", java.time.LocalDateTime.now(),
            "message", "Sales Order Management System is running"
        ));
    }

    // ========== LEGACY ENDPOINT ==========
    
    @PostMapping("/place-order")
    public ResponseEntity<?> placeOrder(@RequestBody Order order) {
        try {
            // This is a simplified version for backward compatibility
            return ResponseEntity.ok("Order processing endpoint - use /orders for full functionality");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
