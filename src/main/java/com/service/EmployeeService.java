package com.service;

import com.dto.EmployeeDTO;
import com.dto.EmployeeCreateRequest;
import com.dto.EmployeeUpdateRequest;

import java.util.List;

public interface EmployeeService {

    EmployeeDTO createEmployee(EmployeeCreateRequest request);

    EmployeeDTO getEmployeeById(Long id);

    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO updateEmployee(Long id, EmployeeUpdateRequest request);

    void deleteEmployee(Long id);
}
