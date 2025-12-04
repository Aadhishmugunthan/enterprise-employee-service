package com.examplee.employee.service.impl;

import com.examplee.employee.dto.EmployeeDTO;
import com.examplee.employee.dto.EmployeeCreateRequest;
import com.examplee.employee.dto.EmployeeUpdateRequest;
import com.examplee.employee.entity.EmployeeEntity;
import com.examplee.employee.exception.ResourceNotFoundException;
import com.examplee.employee.exception.ValidationException;
import com.examplee.employee.mapper.EmployeeMapper;
import com.examplee.employee.repository.EmployeeRepository;
import com.examplee.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final EmployeeMapper mapper;

    @Override
    public EmployeeDTO createEmployee(EmployeeCreateRequest request) {
        if (repository.existsByEmail(request.getEmail())) {
            throw new ValidationException("Email already exists");
        }
        EmployeeEntity entity = mapper.toEntity(request);
        EmployeeEntity saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        EmployeeEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        return mapper.toDto(entity);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeUpdateRequest request) {
        EmployeeEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        if (repository.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new ValidationException("Email already taken by another employee");
        }

        mapper.updateEntityFromRequest(request, entity);
        EmployeeEntity updated = repository.save(entity);
        return mapper.toDto(updated);
    }

    @Override
    public void deleteEmployee(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Employee not found");
        }
        repository.deleteById(id);
    }
}
