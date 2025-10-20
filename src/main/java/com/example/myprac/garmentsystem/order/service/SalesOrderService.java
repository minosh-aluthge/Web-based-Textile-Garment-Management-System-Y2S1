package com.example.myprac.garmentsystem.order.service;

import com.example.myprac.garmentsystem.order.model.Customer;
import com.example.myprac.garmentsystem.order.model.Order;
import com.example.myprac.garmentsystem.order.model.OrderItem;
import com.example.myprac.garmentsystem.order.model.Product;
import com.example.myprac.garmentsystem.order.repositoty.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
public class SalesOrderService {

    private final OrderRepository orderRepository;
    
    private final CustomerRepository customerRepository;
    
    private final ProductRepository productRepository;
    
    private final OrderItemRepository orderItemRepository;
    
    private final InventoryRepository inventoryRepository;

    @Autowired
    public SalesOrderService(
            OrderRepository orderRepository,
            CustomerRepository customerRepository,
            ProductRepository productRepository,
            OrderItemRepository orderItemRepository,
            InventoryRepository inventoryRepository
    ) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.inventoryRepository = inventoryRepository;
    }

    // CREATE Order (C in CRUD)
    public Order createOrder(Order order, List<OrderItem> orderItems) {
        // Validate customer exists
        if (order.getCustomer() == null || order.getCustomer().getId() == null) {
            throw new RuntimeException("Customer is required");
        }
        
        Customer customer = customerRepository.findById(order.getCustomer().getId())
            .orElseThrow(() -> new RuntimeException("Customer not found"));
        
        order.setCustomer(customer);
        order.setStatus("PENDING");
        order.setOrderDate(LocalDateTime.now());
        
        // Calculate total amount
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderItem item : orderItems) {
            Product product = productRepository.findById(item.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found: " + item.getProduct().getId()));
            
            item.setProduct(product);
            item.setOrder(order);
            item.setUnitPrice(product.getStandardPrice());
            totalAmount = totalAmount.add(item.getTotalPrice());
        }
        
        order.setTotalAmount(totalAmount);
        Order savedOrder = orderRepository.save(order);
        
        // Save order items
        for (OrderItem item : orderItems) {
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        }
        
        return savedOrder;
    }

    // READ Orders: list all (R in CRUD)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    // READ Orders by status (R in CRUD - filtered)
    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }
    
    // READ Orders by customer (R in CRUD - filtered)
    public List<Order> getOrdersByCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found"));
        return orderRepository.findByCustomer(customer);
    }
    
    // READ Orders by search term (R in CRUD - search)
    public List<Order> searchOrders(String searchTerm) {
        return orderRepository.findByCustomerNameOrSubjectContaining(searchTerm, searchTerm);
    }

    // READ Order by ID (R in CRUD)
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // UPDATE Order (U in CRUD)
    public Order updateOrder(Long orderId, Order updatedOrder) {
        Order existingOrder = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
        
        existingOrder.setSubject(updatedOrder.getSubject());
        existingOrder.setDescription(updatedOrder.getDescription());
        existingOrder.setDeliveryDate(updatedOrder.getDeliveryDate());
        existingOrder.setStatus(updatedOrder.getStatus());
        existingOrder.setUpdatedAt(LocalDateTime.now());
        
        return orderRepository.save(existingOrder);
    }

    // DELETE Order (D in CRUD)
    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
        
        // Delete order items first
        orderItemRepository.deleteByOrderId(orderId);
        
        // Delete order
        orderRepository.delete(order);
    }

    // UPDATE Order production stage (U in CRUD - domain specific)
    public Order updateOrderStage(Long orderId, String productionStage, Integer stageIndex, String detailsJson) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setProductionStage(productionStage);
        order.setProductionStageIndex(stageIndex);
        // Append details to per-stage history JSON { "Cutting": [ {...}, ... ], "Sewing": [ ... ] }
        if (detailsJson != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, List<Map<String, Object>>> history;
                String existing = order.getProductionStageDetails();
                if (existing != null && !existing.isBlank()) {
                    history = mapper.readValue(existing, new TypeReference<Map<String, List<Map<String, Object>>>>() {});
                } else {
                    history = new HashMap<>();
                }

                // parse incoming details
                Map<String, Object> entry = mapper.readValue(detailsJson, new TypeReference<Map<String, Object>>() {});
                // ensure stage saved in entry
                entry.putIfAbsent("stage", productionStage);
                // ensure timestamp
                entry.putIfAbsent("updatedAt", java.time.Instant.now().toString());

                // If productStatus provided, mirror it into Order.status
                Object ps = entry.get("productStatus");
                if (ps instanceof String && !((String) ps).isBlank()) {
                    order.setStatus(((String) ps).toUpperCase());
                }

                history.computeIfAbsent(productionStage, k -> new ArrayList<>()).add(entry);

                order.setProductionStageDetails(mapper.writeValueAsString(history));
            } catch (Exception ex) {
                // fallback: store raw detailsJson if parsing fails
                order.setProductionStageDetails(detailsJson);
            }
        }
        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    // CREATE Customer (C in CRUD)
    public Customer createCustomer(Customer customer) {
        if (customerRepository.findByEmail(customer.getEmail()).isPresent()) {
            throw new RuntimeException("Customer with email already exists");
        }
        return customerRepository.save(customer);
    }
    
    // READ Customers: list all (R in CRUD)
    public List<Customer> getAllCustomers() {
        return customerRepository.findByIsActiveTrue();
    }
    
    // READ Customer by ID (R in CRUD)
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }
    
    // UPDATE Customer (U in CRUD)
    public Customer updateCustomer(Long customerId, Customer updatedCustomer) {
        Customer existingCustomer = customerRepository.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found"));
        
        existingCustomer.setName(updatedCustomer.getName());
        existingCustomer.setEmail(updatedCustomer.getEmail());
        existingCustomer.setPhone(updatedCustomer.getPhone());
        existingCustomer.setAddress(updatedCustomer.getAddress());
        
        return customerRepository.save(existingCustomer);
    }
    
    // DELETE Customer (D in CRUD - soft delete)
    public void deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found"));
        
        customer.setIsActive(false);
        customerRepository.save(customer);
    }

    // READ Products: list all (R in CRUD)
    public List<Product> getAllProducts() {
        return productRepository.findByIsActiveTrue();
    }
    
    // READ Product by ID (R in CRUD)
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    
    // READ Products by category (R in CRUD - filtered)
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }
    
    // CREATE Product (C in CRUD)
    public Product createProduct(Product product) {
        if (productRepository.findByName(product.getName()).isPresent()) {
            throw new RuntimeException("Product with name already exists");
        }
        return productRepository.save(product);
    }
    
    // UPDATE Product (U in CRUD)
    public Product updateProduct(Long productId, Product updatedProduct) {
        Product existingProduct = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setCategory(updatedProduct.getCategory());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setStandardPrice(updatedProduct.getStandardPrice());
        // Extended apparel fields
        existingProduct.setSizeRange(updatedProduct.getSizeRange());
        existingProduct.setFabricType(updatedProduct.getFabricType());
        existingProduct.setColorOptions(updatedProduct.getColorOptions());
        existingProduct.setCareInstructions(updatedProduct.getCareInstructions());
        existingProduct.setAccessories(updatedProduct.getAccessories());
        existingProduct.setMaterial(updatedProduct.getMaterial());
        existingProduct.setWeight(updatedProduct.getWeight());
        existingProduct.setDimensions(updatedProduct.getDimensions());
        
        return productRepository.save(existingProduct);
    }
    
    // DELETE Product (D in CRUD)
    public void deleteProduct(Long productId) {
        // Safe hard delete: block if product is referenced by any order items
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));

        long refs = orderItemRepository.countByProductId(productId);
        if (refs > 0) {
            throw new RuntimeException("Cannot delete product: it is referenced by " + refs + " order item(s)");
        }

        productRepository.deleteById(productId);
    }

    // Record payment outcome for an order
    public Order recordPayment(Long orderId, boolean success, String transactionId, String message) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
        if (success) {
            order.setStatus("PAID");
        } else {
            order.setStatus("PAYMENT_FAILED");
        }
        order.setUpdatedAt(LocalDateTime.now());
        // Optionally, transactionId/message could be appended to productionStageDetails log
        return orderRepository.save(order);
    }
}