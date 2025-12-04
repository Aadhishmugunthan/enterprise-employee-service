package com.examplee.employee.repository;

import com.examplee.employee.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    // Check if email already exists
    boolean existsByEmail(String email);

    // Check if email exists for another employee (used in update)
    boolean existsByEmailAndIdNot(String email, Long id);
}


