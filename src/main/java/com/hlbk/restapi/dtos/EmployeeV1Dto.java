package com.hlbk.restapi.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeV1Dto {

    private Long id;
    private String name;
    private String role;

    public EmployeeV1Dto(@Nullable Long id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }
}
