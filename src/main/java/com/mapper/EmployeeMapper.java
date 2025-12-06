package com.mapper;

import com.dto.EmployeeDTO;
import com.dto.EmployeeCreateRequest;
import com.dto.EmployeeUpdateRequest;
import com.entity.EmployeeEntity;
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
