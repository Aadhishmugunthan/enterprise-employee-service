package com.controller;

import com.dto.EmployeeCreateRequest;
import com.dto.EmployeeDTO;
import com.dto.EmployeeUpdateRequest;
import com.service.EmployeeService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    // CREATE EMPLOYEE
    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeCreateRequest request) {
        EmployeeDTO created = employeeService.createEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET EMPLOYEE BY ID
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        EmployeeDTO dto = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(dto);
    }

    // GET ALL EMPLOYEES
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> list = employeeService.getAllEmployees();
        return ResponseEntity.ok(list);
    }

    // UPDATE EMPLOYEE
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeUpdateRequest request) {

        EmployeeDTO updated = employeeService.updateEmployee(id, request);
        return ResponseEntity.ok(updated);
    }

    // DELETE EMPLOYEE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
