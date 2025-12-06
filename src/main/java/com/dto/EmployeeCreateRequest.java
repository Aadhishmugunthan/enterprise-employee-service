package com.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeCreateRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String department;
}
