package com.hlbk.restapi.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeV2Dto {

    private Long id;
    private String firstName;
    private String lastName;
    private String role;

    public EmployeeV2Dto(@Nullable Long id, String firstName, String lastName, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }
}
