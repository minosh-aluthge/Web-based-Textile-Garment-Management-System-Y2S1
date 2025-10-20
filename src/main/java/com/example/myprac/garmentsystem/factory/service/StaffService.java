package com.example.myprac.garmentsystem.factory.service;

import com.example.myprac.garmentsystem.factory.model.Staff;
import com.example.myprac.garmentsystem.factory.repository.StaffRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StaffService {
    private final StaffRepository repo;

    public StaffService(StaffRepository repo) {
        this.repo = repo;
    }

    public List<Staff> list(){ return repo.findAll(); }
    public Optional<Staff> get(Long id){ return repo.findById(id); }
    public Staff create(Staff s){ return repo.save(s); }
    public Staff update(Long id, Staff req){
        return repo.findById(id).map(s -> {
            if (req.getName() != null) s.setName(req.getName());
            if (req.getEmail() != null) s.setEmail(req.getEmail());
            if (req.getPhone() != null) s.setPhone(req.getPhone());
            if (req.getDepartment() != null) s.setDepartment(req.getDepartment());
            if (req.getRole() != null) s.setRole(req.getRole());
            if (req.getShift() != null) s.setShift(req.getShift());
            if (req.getHourlyRate() != null) s.setHourlyRate(req.getHourlyRate());
            if (req.getIsActive() != null) s.setIsActive(req.getIsActive());
            return repo.save(s);
        }).orElseThrow(() -> new IllegalArgumentException("Staff not found: " + id));
    }
    public void delete(Long id){ repo.deleteById(id); }
}
