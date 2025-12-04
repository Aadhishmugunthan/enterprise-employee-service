package com.examplee.employee.service;

import com.examplee.employee.dto.EmployeeDTO;
import com.examplee.employee.dto.EmployeeCreateRequest;
import com.examplee.employee.dto.EmployeeUpdateRequest;

import java.util.List;

public interface EmployeeService {

    EmployeeDTO createEmployee(EmployeeCreateRequest request);

    EmployeeDTO getEmployeeById(Long id);

    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO updateEmployee(Long id, EmployeeUpdateRequest request);

    void deleteEmployee(Long id);
}
