package com.example.myprac.garmentsystem.production.repository;

import com.example.myprac.garmentsystem.production.model.ProductionJob;
import com.example.myprac.garmentsystem.production.model.ProductionStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductionJobRepository extends JpaRepository<ProductionJob, Long> {
    List<ProductionJob> findByStage(ProductionStage stage);
}
