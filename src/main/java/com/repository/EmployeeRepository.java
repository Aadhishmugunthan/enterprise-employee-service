package com.repository;

import com.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    boolean existsByEmail(String email);

    Optional<EmployeeEntity> findByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

}
