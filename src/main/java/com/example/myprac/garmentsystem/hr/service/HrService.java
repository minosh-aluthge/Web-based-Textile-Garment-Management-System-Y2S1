package com.example.myprac.garmentsystem.hr.service;

import com.example.myprac.garmentsystem.hr.model.Attendance;
import com.example.myprac.garmentsystem.hr.model.Employee;
import com.example.myprac.garmentsystem.hr.model.LeaveRequest;
import com.example.myprac.garmentsystem.hr.model.LeaveStatus;
import com.example.myprac.garmentsystem.hr.repository.AttendanceRepository;
import com.example.myprac.garmentsystem.hr.repository.EmployeeRepository;
import com.example.myprac.garmentsystem.hr.repository.LeaveRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class HrService {
    private final EmployeeRepository employeeRepository;
    private final AttendanceRepository attendanceRepository;
    private final LeaveRequestRepository leaveRepository;

    public HrService(EmployeeRepository employeeRepository, AttendanceRepository attendanceRepository, LeaveRequestRepository leaveRepository) {
        this.employeeRepository = employeeRepository;
        this.attendanceRepository = attendanceRepository;
        this.leaveRepository = leaveRepository;
    }

    // Employees
    public List<Employee> listEmployees(){
        // Primary: active employees
        List<Employee> active = employeeRepository.findByIsActiveTrue();
        List<Employee> base = (active != null && !active.isEmpty()) ? active : employeeRepository.findAll();
        return base.stream()
                .filter(e -> e != null)
                // Treat null as active to support legacy rows
                .filter(e -> e.getIsActive() == null || Boolean.TRUE.equals(e.getIsActive()))
                .filter(e -> e.getName() != null && !e.getName().trim().isEmpty())
                .collect(Collectors.toList());
    }
    public Optional<Employee> getEmployee(Long id){ return employeeRepository.findById(id); }
    public Employee createEmployee(Employee e){ return employeeRepository.save(e); }
    public Employee updateEmployee(Long id, Employee req){
        return employeeRepository.findById(id).map(e -> {
            e.setName(req.getName());
            e.setEmail(req.getEmail());
            e.setPhone(req.getPhone());
            e.setDepartment(req.getDepartment());
            e.setRole(req.getRole());
            e.setMonthlySalary(req.getMonthlySalary());
            e.setIsActive(req.getIsActive());
            return employeeRepository.save(e);
        }).orElseThrow(() -> new IllegalArgumentException("Employee not found: " + id));
    }
    public void deleteEmployee(Long id){
        Employee e = employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Employee not found: "+id));
        long refs = attendanceRepository.countByEmployee(e);
        if (refs > 0) {
            throw new IllegalStateException("Cannot delete employee: has " + refs + " attendance record(s)");
        }
        employeeRepository.deleteById(id);
    }

    // Attendance
    public List<Attendance> listAttendance(){ return attendanceRepository.findAll(); }
    public List<Attendance> listAttendanceByDate(LocalDate date){ return attendanceRepository.findByDate(date); }
    public List<Attendance> listAttendanceByEmployee(Long empId){
        Employee e = employeeRepository.findById(empId).orElseThrow(() -> new IllegalArgumentException("Employee not found: "+empId));
        return attendanceRepository.findByEmployee(e);
    }
    public Attendance markAttendance(Long empId, LocalDate date, String status, String note){
        Employee e = employeeRepository.findById(empId).orElseThrow(() -> new IllegalArgumentException("Employee not found: "+empId));
        Attendance a = new Attendance();
        a.setEmployee(e);
        a.setDate(date != null ? date : LocalDate.now());
        a.setStatus(status != null ? status : "Present");
        a.setNote(note);
        return attendanceRepository.save(a);
    }

    // Leave Requests
    public List<LeaveRequest> listLeave(){ return leaveRepository.findAll(); }

    public LeaveRequest createLeave(Long empId, java.time.LocalDate start, java.time.LocalDate end, String type){
        Employee e = employeeRepository.findById(empId).orElseThrow(() -> new IllegalArgumentException("Employee not found: "+empId));
        if (start == null) {
            throw new IllegalArgumentException("Start date is required");
        }
        // Strict: end must be AFTER start if provided
        if (end != null && !end.isAfter(start)) {
            throw new IllegalArgumentException("End date must be after start date");
        }
        LeaveRequest lr = new LeaveRequest();
        lr.setEmployee(e);
        lr.setStartDate(start);
        lr.setEndDate(end);
        lr.setType(type);
        lr.setStatus(LeaveStatus.PENDING);
        return leaveRepository.saveAndFlush(lr);
    }

    public LeaveRequest approveLeave(Long id){
        LeaveRequest lr = leaveRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Leave not found: "+id));
        lr.setStatus(LeaveStatus.APPROVED);
        return leaveRepository.saveAndFlush(lr);
    }

    public LeaveRequest rejectLeave(Long id){
        LeaveRequest lr = leaveRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Leave not found: "+id));
        lr.setStatus(LeaveStatus.REJECTED);
        return leaveRepository.saveAndFlush(lr);
    }

    public LeaveRequest updateLeave(Long id, java.time.LocalDate start, java.time.LocalDate end, String type, LeaveStatus status){
        LeaveRequest lr = leaveRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Leave not found: "+id));
        if (start != null) lr.setStartDate(start);
        if (end != null) {
            if (start != null && !end.isAfter(start)) throw new IllegalArgumentException("End date must be after start date");
            if (start == null && !end.isAfter(lr.getStartDate())) throw new IllegalArgumentException("End date must be after start date");
            lr.setEndDate(end);
        }
        if (type != null) lr.setType(type);
        if (status != null) lr.setStatus(status);
        return leaveRepository.saveAndFlush(lr);
    }
}
