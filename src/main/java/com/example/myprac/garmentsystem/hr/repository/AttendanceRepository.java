package com.example.myprac.garmentsystem.hr.repository;

import com.example.myprac.garmentsystem.hr.model.Attendance;
import com.example.myprac.garmentsystem.hr.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByEmployee(Employee employee);
    List<Attendance> findByDate(LocalDate date);
    List<Attendance> findByEmployeeAndDate(Employee employee, LocalDate date);
    long countByEmployee(Employee employee);
}
