package com.example.myprac.garmentsystem.order.repositoty;

import com.example.myprac.garmentsystem.order.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // READ Customer by email (R - lookup)
    Optional<Customer> findByEmail(String email);
    
    // READ active Customers (R - filtered)
    List<Customer> findByIsActiveTrue();
    
    // READ search by name or email (R - search)
    @Query("SELECT c FROM Customer c WHERE c.name LIKE %:name% OR c.email LIKE %:email%")
    List<Customer> findByNameOrEmailContaining(@Param("name") String name, @Param("email") String email);
}
