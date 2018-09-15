package com.hlbk.restapi.converters;

import com.hlbk.restapi.dtos.EmployeeV1Dto;
import com.hlbk.restapi.models.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeConverter {

    public EmployeeV1Dto toEmployeeV1Dto(Employee employee) {
        return new EmployeeV1Dto(employee.getId(), employee.getName(), employee.getRole());
    }

    public Employee toEmployee(EmployeeV1Dto employeeV1Dto) {
        return new Employee(employeeV1Dto.getId(), employeeV1Dto.getName(), employeeV1Dto.getRole());
    }
}
