package com.example.myprac.garmentsystem.hr.controller;

import com.example.myprac.garmentsystem.hr.model.Attendance;
import com.example.myprac.garmentsystem.hr.model.LeaveRequest;
import com.example.myprac.garmentsystem.hr.model.Employee;
import com.example.myprac.garmentsystem.hr.service.HrService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/hr")
@CrossOrigin
public class HrController {

    private final HrService hrService;

    public HrController(HrService hrService) {
        this.hrService = hrService;
    }

    // Employees
    @GetMapping("/employees")
    public List<Employee> listEmployees(){ return hrService.listEmployees(); }

    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee e){ return hrService.createEmployee(e); }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id){
        return hrService.getEmployee(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/employees/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee e){ return hrService.updateEmployee(id, e); }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id){
        try {
            hrService.deleteEmployee(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // Attendance
    @GetMapping("/attendance")
    public List<Attendance> listAttendance(@RequestParam(value = "date", required = false)
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        if (date != null) return hrService.listAttendanceByDate(date);
        return hrService.listAttendance();
    }

    @GetMapping("/attendance/by-employee/{empId}")
    public List<Attendance> listAttendanceByEmployee(@PathVariable Long empId){
        return hrService.listAttendanceByEmployee(empId);
    }

    @PostMapping("/attendance/mark")
    public Attendance markAttendance(@RequestParam Long empId,
                                     @RequestParam(required = false)
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                     @RequestParam(defaultValue = "Present") String status,
                                     @RequestParam(required = false) String note){
        return hrService.markAttendance(empId, date, status, note);
    }

    // Leave Requests
    @GetMapping("/leave")
    public List<LeaveRequest> listLeave(){
        return hrService.listLeave();
    }

    @PostMapping("/leave")
    public ResponseEntity<?> createLeave(@RequestParam Long empId,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
                                    @RequestParam String type){
        try {
            return ResponseEntity.ok(hrService.createLeave(empId, start, end, type));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/leave/{id}/approve")
    public ResponseEntity<?> approveLeave(@PathVariable Long id){
        try {
            return ResponseEntity.ok(hrService.approveLeave(id));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/leave/{id}/reject")
    public ResponseEntity<?> rejectLeave(@PathVariable Long id){
        try {
            return ResponseEntity.ok(hrService.rejectLeave(id));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
