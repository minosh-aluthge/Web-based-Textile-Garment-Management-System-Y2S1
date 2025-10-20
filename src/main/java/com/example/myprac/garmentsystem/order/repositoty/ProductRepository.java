package com.example.myprac.garmentsystem.order.repositoty;

import com.example.myprac.garmentsystem.order.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    List<Product> findByIsActiveTrue();
    
    List<Product> findByCategory(String category);
    
    Optional<Product> findByName(String name);
    
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name% OR p.category LIKE %:category%")
    List<Product> findByNameOrCategoryContaining(@Param("name") String name, @Param("category") String category);
}
