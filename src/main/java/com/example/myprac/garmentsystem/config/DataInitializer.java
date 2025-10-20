package com.example.myprac.garmentsystem.config;

import com.example.myprac.garmentsystem.order.model.*;
import com.example.myprac.garmentsystem.order.repositoty.*;
import com.example.myprac.garmentsystem.hr.model.Employee;
import com.example.myprac.garmentsystem.hr.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private OrderItemRepository orderItemRepository;
    
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== STARTING DATA INITIALIZATION ===");
        
        initializeEmployees();
        System.out.println("✅ Employees initialized");

        initializeCustomers();
        System.out.println("✅ Customers initialized");
        
        initializeProducts();
        System.out.println("✅ Products initialized");
        
        initializeInventory();
        System.out.println("✅ Inventory initialized");
        
        initializeOrders();
        System.out.println("✅ Orders initialized");
        
        System.out.println("=== DATA INITIALIZATION COMPLETE ===");
        System.out.println("Sample data loaded successfully!");
        System.out.println("You can now access the application at: http://localhost:8080");
    }

    private void initializeEmployees() {
        if (employeeRepository.count() == 0) {
            System.out.println("Creating sample employees...");

            Employee e1 = new Employee();
            e1.setName("Alex Patel");
            e1.setEmail("alex.patel@example.com");
            e1.setPhone("+1-555-1001");
            e1.setDepartment("Quality");
            e1.setRole("Inspector");
            e1.setIsActive(true);

            Employee e2 = new Employee();
            e2.setName("Maria Gomez");
            e2.setEmail("maria.gomez@example.com");
            e2.setPhone("+1-555-1002");
            e2.setDepartment("Quality");
            e2.setRole("Senior Inspector");
            e2.setIsActive(true);

            Employee e3 = new Employee();
            e3.setName("John Reed");
            e3.setEmail("john.reed@example.com");
            e3.setPhone("+1-555-1003");
            e3.setDepartment("Production");
            e3.setRole("Supervisor");
            e3.setIsActive(true);

            Employee e4 = new Employee();
            e4.setName("Priya Nair");
            e4.setEmail("priya.nair@example.com");
            e4.setPhone("+1-555-1004");
            e4.setDepartment("Quality");
            e4.setRole("Inspector");
            e4.setIsActive(true);

            Employee e5 = new Employee();
            e5.setName("Liam Chen");
            e5.setEmail("liam.chen@example.com");
            e5.setPhone("+1-555-1005");
            e5.setDepartment("Quality");
            e5.setRole("Inspector");
            e5.setIsActive(true);

            employeeRepository.saveAll(java.util.List.of(e1, e2, e3, e4, e5));
        }
    }

    private void initializeCustomers() {
        if (customerRepository.count() == 0) {
            System.out.println("Creating sample customers...");
            // Create sample customers
            Customer customer1 = new Customer("John Smith", "john.smith@email.com", "+1-555-0101", "123 Main St, New York, NY 10001");
            Customer customer2 = new Customer("Sarah Johnson", "sarah.johnson@email.com", "+1-555-0102", "456 Oak Ave, Los Angeles, CA 90210");
            Customer customer3 = new Customer("Mike Wilson", "mike.wilson@email.com", "+1-555-0103", "789 Pine St, Chicago, IL 60601");
            Customer customer4 = new Customer("Emily Davis", "emily.davis@email.com", "+1-555-0104", "321 Elm St, Houston, TX 77001");
            Customer customer5 = new Customer("David Brown", "david.brown@email.com", "+1-555-0105", "654 Maple Dr, Phoenix, AZ 85001");

            customerRepository.save(customer1);
            customerRepository.save(customer2);
            customerRepository.save(customer3);
            customerRepository.save(customer4);
            customerRepository.save(customer5);
        }
    }

    private void initializeProducts() {
        if (productRepository.count() == 0) {
            System.out.println("Creating sample products...");
            // Create sample products
            Product product1 = new Product("Cotton T-Shirt", "Shirts", "100% Cotton comfortable t-shirt", new BigDecimal("19.99"));
            Product product2 = new Product("Denim Jeans", "Pants", "Classic blue denim jeans", new BigDecimal("49.99"));
            Product product3 = new Product("Summer Dress", "Dresses", "Light and airy summer dress", new BigDecimal("39.99"));
            Product product4 = new Product("Leather Belt", "Accessories", "Genuine leather belt", new BigDecimal("24.99"));
            Product product5 = new Product("Wool Sweater", "Shirts", "Warm wool sweater for winter", new BigDecimal("59.99"));
            Product product6 = new Product("Running Shoes", "Accessories", "Comfortable running shoes", new BigDecimal("79.99"));
            Product product7 = new Product("Formal Blazer", "Shirts", "Professional formal blazer", new BigDecimal("89.99"));
            Product product8 = new Product("Casual Shorts", "Pants", "Comfortable casual shorts", new BigDecimal("29.99"));

            productRepository.save(product1);
            productRepository.save(product2);
            productRepository.save(product3);
            productRepository.save(product4);
            productRepository.save(product5);
            productRepository.save(product6);
            productRepository.save(product7);
            productRepository.save(product8);
        }
    }

    private void initializeInventory() {
        if (inventoryRepository.count() == 0) {
            System.out.println("Creating sample inventory...");
            // Create inventory entries
            Inventory inv1 = new Inventory("Cotton T-Shirt", 100);
            Inventory inv2 = new Inventory("Denim Jeans", 75);
            Inventory inv3 = new Inventory("Summer Dress", 50);
            Inventory inv4 = new Inventory("Leather Belt", 200);
            Inventory inv5 = new Inventory("Wool Sweater", 30);
            Inventory inv6 = new Inventory("Running Shoes", 60);
            Inventory inv7 = new Inventory("Formal Blazer", 25);
            Inventory inv8 = new Inventory("Casual Shorts", 80);

            inventoryRepository.save(inv1);
            inventoryRepository.save(inv2);
            inventoryRepository.save(inv3);
            inventoryRepository.save(inv4);
            inventoryRepository.save(inv5);
            inventoryRepository.save(inv6);
            inventoryRepository.save(inv7);
            inventoryRepository.save(inv8);
        }
    }

    private void initializeOrders() {
        if (orderRepository.count() == 0) {
            System.out.println("Creating sample orders...");
            // Get customers and products by ID (safer approach)
            List<Customer> customers = customerRepository.findAll();
            List<Product> products = productRepository.findAll();

            if (!customers.isEmpty() && !products.isEmpty()) {
                // Create sample orders using first available customer and products
                Customer customer1 = customers.get(0);
                Product product1 = products.get(0);
                
                if (products.size() > 1) {
                    Product product2 = products.get(1);
                    
                    // Create first order
                    Order order1 = new Order(customer1, "Summer Clothing Order", "Order for summer collection");
                    order1.setStatus("CONFIRMED");
                    order1.setDeliveryDate(LocalDateTime.now().plusDays(7));
                    order1.setCreatedBy("Sales Executive");
                    orderRepository.save(order1);

                    // Add order items
                    OrderItem item1 = new OrderItem(order1, product1, 2, product1.getStandardPrice());
                    OrderItem item2 = new OrderItem(order1, product2, 1, product2.getStandardPrice());
                    orderItemRepository.save(item1);
                    orderItemRepository.save(item2);

                    // Update order total
                    order1.setTotalAmount(item1.getTotalPrice().add(item2.getTotalPrice()));
                    orderRepository.save(order1);
                }

                // Create second order if we have more customers and products
                if (customers.size() > 1 && products.size() > 2) {
                    Customer customer2 = customers.get(1);
                    Product product3 = products.get(2);
                    
                    Order order2 = new Order(customer2, "Dress Order", "Special occasion dress order");
                    order2.setStatus("PENDING");
                    order2.setDeliveryDate(LocalDateTime.now().plusDays(5));
                    order2.setCreatedBy("Sales Executive");
                    orderRepository.save(order2);

                    OrderItem item3 = new OrderItem(order2, product3, 1, product3.getStandardPrice());
                    orderItemRepository.save(item3);

                    order2.setTotalAmount(item3.getTotalPrice());
                    orderRepository.save(order2);
                }

                // Create third order if we have more customers and products
                if (customers.size() > 2 && products.size() > 3) {
                    Customer customer3 = customers.get(2);
                    Product product4 = products.get(3);
                    
                    Order order3 = new Order(customer3, "Accessories Order", "Belt and accessories order");
                    order3.setStatus("PROCESSING");
                    order3.setDeliveryDate(LocalDateTime.now().plusDays(3));
                    order3.setCreatedBy("Sales Executive");
                    orderRepository.save(order3);

                    OrderItem item4 = new OrderItem(order3, product4, 2, product4.getStandardPrice());
                    orderItemRepository.save(item4);

                    order3.setTotalAmount(item4.getTotalPrice());
                    orderRepository.save(order3);
                }
            }
        }
    }
}
