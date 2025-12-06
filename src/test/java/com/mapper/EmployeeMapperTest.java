package com.examplee.employee.mapper;

import com.dto.EmployeeCreateRequest;
import com.dto.EmployeeDTO;
import com.dto.EmployeeUpdateRequest;
import com.entity.EmployeeEntity;
import com.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EmployeeMapperTest {

    private final EmployeeMapper mapper = Mappers.getMapper(EmployeeMapper.class);

    @Test
    void toDto_shouldMapEntityToDto() {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setId(1L);
        entity.setFirstName("Aadhi");
        entity.setLastName("M");
        entity.setEmail("aadhi@example.com");
        entity.setDepartment("IT");

        EmployeeDTO dto = mapper.toDto(entity);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getFirstName()).isEqualTo("Aadhi");
        assertThat(dto.getLastName()).isEqualTo("M");
        assertThat(dto.getEmail()).isEqualTo("aadhi@example.com");
        assertThat(dto.getDepartment()).isEqualTo("IT");
    }

    @Test
    void toEntity_shouldMapCreateRequestToEntity() {
        EmployeeCreateRequest request = new EmployeeCreateRequest();
        request.setFirstName("Aadhi");
        request.setLastName("M");
        request.setEmail("aadhi@example.com");
        request.setDepartment("IT");

        EmployeeEntity entity = mapper.toEntity(request);

        assertThat(entity.getId()).isNull();
        assertThat(entity.getFirstName()).isEqualTo("Aadhi");
        assertThat(entity.getEmail()).isEqualTo("aadhi@example.com");
    }

    @Test
    void updateEntityFromRequest_shouldUpdateFields() {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setId(1L);
        entity.setFirstName("Old");
        entity.setLastName("Name");
        entity.setEmail("old@example.com");
        entity.setDepartment("HR");

        EmployeeUpdateRequest request = new EmployeeUpdateRequest();
        request.setFirstName("New");
        request.setLastName("Name");
        request.setEmail("new@example.com");
        request.setDepartment("IT");

        mapper.updateEntityFromRequest(request, entity);

        assertThat(entity.getId()).isEqualTo(1L); // id unchanged
        assertThat(entity.getFirstName()).isEqualTo("New");
        assertThat(entity.getEmail()).isEqualTo("new@example.com");
        assertThat(entity.getDepartment()).isEqualTo("IT");
    }

}
