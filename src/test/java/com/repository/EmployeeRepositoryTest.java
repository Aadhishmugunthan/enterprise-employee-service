package com.repository;

import com.entity.EmployeeEntity;
import com.repository.EmployeeRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")   // <-- use application-test.properties
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository repository;

    @Test
    void existsByEmail_shouldReturnTrueWhenEmailPresent() {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setFirstName("Aadhi");
        entity.setLastName("M");
        entity.setEmail("test@example.com");
        entity.setDepartment("IT");

        repository.save(entity);

        boolean exists = repository.existsByEmail("test@example.com");
        assertThat(exists).isTrue();
    }

    @Test
    void findByEmail_shouldReturnEmployee() {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setFirstName("Aadhi");
        entity.setLastName("M");
        entity.setEmail("test2@example.com");
        entity.setDepartment("IT");

        repository.save(entity);

        Optional<EmployeeEntity> found = repository.findByEmail("test2@example.com");

        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("test2@example.com");
    }

    @Test
    void existsByEmailAndIdNot_shouldReturnFalseForSameEmployee() {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setFirstName("Aadhi");
        entity.setLastName("M");
        entity.setEmail("unique@example.com");
        entity.setDepartment("IT");

        EmployeeEntity saved = repository.save(entity);

        boolean exists = repository.existsByEmailAndIdNot("unique@example.com", saved.getId());
        assertThat(exists).isFalse();
    }

    @Test
    void existsByEmailAndIdNot_shouldReturnTrueForDifferentEmployee() {
        EmployeeEntity entity1 = new EmployeeEntity();
        entity1.setFirstName("Aadhi");
        entity1.setLastName("M");
        entity1.setEmail("duplicate@example.com");
        entity1.setDepartment("IT");

        repository.save(entity1);

        EmployeeEntity entity2 = new EmployeeEntity();
        entity2.setFirstName("Someone");
        entity2.setLastName("Else");
        entity2.setEmail("other@example.com");
        entity2.setDepartment("HR");

        EmployeeEntity saved2 = repository.save(entity2);

        boolean exists = repository.existsByEmailAndIdNot("duplicate@example.com", saved2.getId());
        assertThat(exists).isTrue();
    }
}
