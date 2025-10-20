package com.example.myprac.garmentsystem.production.controller;

import com.example.myprac.garmentsystem.production.model.ProductionJob;
import com.example.myprac.garmentsystem.production.model.ProductionStage;
import com.example.myprac.garmentsystem.production.service.ProductionJobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/production/jobs")
@CrossOrigin
public class ProductionJobController {

    private final ProductionJobService service;

    public ProductionJobController(ProductionJobService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductionJob> list(@RequestParam(value = "stage", required = false) ProductionStage stage){
        if (stage != null) return service.listByStage(stage);
        return service.list();
    }

    @PostMapping
    public ProductionJob create(@RequestBody ProductionJob j){ return service.create(j); }

    @GetMapping("/{id}")
    public ResponseEntity<ProductionJob> get(@PathVariable Long id){
        return service.get(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ProductionJob update(@PathVariable Long id, @RequestBody ProductionJob j){ return service.update(id, j); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
