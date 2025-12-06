package com.service.impl;

import com.dto.EmployeeCreateRequest;
import com.dto.EmployeeDTO;
import com.dto.EmployeeUpdateRequest;
import com.entity.EmployeeEntity;
import com.exception.ResourceNotFoundException;
import com.exception.ValidationException;
import com.mapper.EmployeeMapper;
import com.repository.EmployeeRepository;
import com.service.impl.EmployeeServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository repository;

    @Mock
    private EmployeeMapper mapper;

    @InjectMocks
    private EmployeeServiceImpl service;

    @Test
    void createEmployee_success() {
        EmployeeCreateRequest request = new EmployeeCreateRequest();
        request.setFirstName("Aadhi");
        request.setLastName("M");
        request.setEmail("test@example.com");
        request.setDepartment("IT");

        EmployeeEntity entity = new EmployeeEntity();
        EmployeeEntity saved = new EmployeeEntity();
        saved.setId(1L);

        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(1L);

        when(repository.existsByEmail("test@example.com")).thenReturn(false);
        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(mapper.toDto(saved)).thenReturn(dto);

        EmployeeDTO result = service.createEmployee(request);

        assertThat(result.getId()).isEqualTo(1L);
        verify(repository).save(entity);
    }

    @Test
    void createEmployee_duplicateEmail() {
        EmployeeCreateRequest request = new EmployeeCreateRequest();
        request.setEmail("dup@example.com");

        when(repository.existsByEmail("dup@example.com")).thenReturn(true);

        assertThrows(ValidationException.class,
                () -> service.createEmployee(request));

        verify(repository, never()).save(any());
    }

    @Test
    void getEmployeeById_found() {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setId(1L);

        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        EmployeeDTO result = service.getEmployeeById(1L);

        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void getEmployeeById_notFound() {
        when(repository.findById(9L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getEmployeeById(9L));
    }

    @Test
    void getAllEmployees_success() {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setId(1L);

        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(1L);

        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        List<EmployeeDTO> list = service.getAllEmployees();

        assertThat(list).hasSize(1);
        assertThat(list.get(0).getId()).isEqualTo(1L);
    }

    @Test
    void updateEmployee_success() {
        Long id = 1L;

        EmployeeUpdateRequest request = new EmployeeUpdateRequest();
        request.setFirstName("Updated");
        request.setEmail("updated@example.com");

        EmployeeEntity existing = new EmployeeEntity();
        existing.setId(id);

        EmployeeEntity updated = new EmployeeEntity();
        updated.setId(id);
        updated.setEmail("updated@example.com");

        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.existsByEmailAndIdNot("updated@example.com", id)).thenReturn(false);

        doAnswer(inv -> {
            EmployeeUpdateRequest r = inv.getArgument(0);
            EmployeeEntity e = inv.getArgument(1);
            e.setFirstName(r.getFirstName());
            e.setEmail(r.getEmail());
            return null;
        }).when(mapper).updateEntityFromRequest(eq(request), eq(existing));

        when(repository.save(existing)).thenReturn(updated);
        when(mapper.toDto(updated)).thenReturn(dto);

        EmployeeDTO result = service.updateEmployee(id, request);

        assertThat(result.getId()).isEqualTo(id);
    }

    @Test
    void updateEmployee_notFound() {
        when(repository.findById(5L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.updateEmployee(5L, new EmployeeUpdateRequest()));
    }

    @Test
    void deleteEmployee_notFound() {
        when(repository.existsById(5L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> service.deleteEmployee(5L));
    }

    @Test
    void deleteEmployee_success() {
        when(repository.existsById(1L)).thenReturn(true);

        service.deleteEmployee(1L);

        verify(repository).deleteById(1L);
    }
}
