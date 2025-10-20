package com.example.myprac.garmentsystem.production.service;

import com.example.myprac.garmentsystem.production.model.ProductionJob;
import com.example.myprac.garmentsystem.production.model.ProductionStage;
import com.example.myprac.garmentsystem.production.repository.ProductionJobRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductionJobService {
    private final ProductionJobRepository repo;

    public ProductionJobService(ProductionJobRepository repo) {
        this.repo = repo;
    }

    public List<ProductionJob> list(){ return repo.findAll(); }
    public List<ProductionJob> listByStage(ProductionStage stage){ return repo.findByStage(stage); }
    public Optional<ProductionJob> get(Long id){ return repo.findById(id); }
    public ProductionJob create(ProductionJob job){ return repo.save(job); }
    public ProductionJob update(Long id, ProductionJob req){
        return repo.findById(id).map(j -> {
            if (req.getOrderId() != null) j.setOrderId(req.getOrderId());
            if (req.getProduct() != null) j.setProduct(req.getProduct());
            if (req.getQuantity() != null) j.setQuantity(req.getQuantity());
            if (req.getStage() != null) j.setStage(req.getStage());
            if (req.getStartDate() != null) j.setStartDate(req.getStartDate());
            if (req.getDueDate() != null) j.setDueDate(req.getDueDate());
            if (req.getNotes() != null) j.setNotes(req.getNotes());
            if (req.getIsActive() != null) j.setIsActive(req.getIsActive());
            return repo.save(j);
        }).orElseThrow(() -> new IllegalArgumentException("Production job not found: " + id));
    }
    public void delete(Long id){ repo.deleteById(id); }
}
