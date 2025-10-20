package com.example.myprac.garmentsystem.order.repositoty;

import com.example.myprac.garmentsystem.order.model.Order;
import com.example.myprac.garmentsystem.order.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    // READ Orders by Customer (R - filtered)
    List<Order> findByCustomer(Customer customer);
    
    // READ Orders by Status (R - filtered)
    List<Order> findByStatus(String status);
    
    // READ Orders within date range (R - filtered)
    @Query("SELECT o FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate")
    List<Order> findByOrderDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    // READ Orders by customer name or subject search (R - search)
    @Query("SELECT o FROM Order o WHERE o.customer.name LIKE %:customerName% OR o.subject LIKE %:subject%")
    List<Order> findByCustomerNameOrSubjectContaining(@Param("customerName") String customerName, @Param("subject") String subject);
}