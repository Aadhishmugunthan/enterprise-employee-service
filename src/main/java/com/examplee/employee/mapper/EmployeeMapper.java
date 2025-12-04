package com.examplee.employee.mapper;

import com.examplee.employee.dto.EmployeeDTO;
import com.examplee.employee.dto.EmployeeCreateRequest;
import com.examplee.employee.dto.EmployeeUpdateRequest;
import com.examplee.employee.entity.EmployeeEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    // CreateRequest → Entity
    EmployeeEntity toEntity(EmployeeCreateRequest request);

    // UpdateRequest → Entity
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(EmployeeUpdateRequest request, @MappingTarget EmployeeEntity entity);

    // Entity → DTO
    EmployeeDTO toDto(EmployeeEntity entity);
}
