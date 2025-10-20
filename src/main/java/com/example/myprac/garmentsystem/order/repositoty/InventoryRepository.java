
package com.example.myprac.garmentsystem.order.repositoty;

import com.example.myprac.garmentsystem.order.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, String> {
    Optional<Inventory> findByProductName(String productName);
}