package com.example.myprac.garmentsystem.factory.controller;

import com.example.myprac.garmentsystem.factory.model.Staff;
import com.example.myprac.garmentsystem.factory.service.StaffService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/factory/staff")
@CrossOrigin
public class StaffController {

    private final StaffService service;

    public StaffController(StaffService service) {
        this.service = service;
    }

    @GetMapping
    public List<Staff> list(){ return service.list(); }

    @PostMapping
    public Staff create(@RequestBody Staff s){ return service.create(s); }

    @GetMapping("/{id}")
    public ResponseEntity<Staff> get(@PathVariable Long id){
        return service.get(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Staff update(@PathVariable Long id, @RequestBody Staff s){ return service.update(id, s); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
