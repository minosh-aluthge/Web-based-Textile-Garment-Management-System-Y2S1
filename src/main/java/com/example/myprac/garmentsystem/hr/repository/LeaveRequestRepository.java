package com.example.myprac.garmentsystem.hr.repository;

import com.example.myprac.garmentsystem.hr.model.Employee;
import com.example.myprac.garmentsystem.hr.model.LeaveRequest;
import com.example.myprac.garmentsystem.hr.model.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByEmployee(Employee employee);
    List<LeaveRequest> findByStatus(LeaveStatus status);
}
