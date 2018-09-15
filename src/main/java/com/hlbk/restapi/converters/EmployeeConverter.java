package com.hlbk.restapi.converters;

import com.hlbk.restapi.dtos.EmployeeV1Dto;
import com.hlbk.restapi.dtos.EmployeeV2Dto;
import com.hlbk.restapi.models.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeConverter {

    public EmployeeV1Dto toEmployeeV1Dto(Employee employee) {
        return new EmployeeV1Dto(employee.getId(), employee.getName(), employee.getRole());
    }

    public EmployeeV2Dto employeeV2Dto(Employee employee) {
        String[] names = employee.getName().split(" ");
        return new EmployeeV2Dto(employee.getId(), names[0], names[1], employee.getRole());
    }

    public Employee toEmployee(EmployeeV1Dto employeeV1Dto) {
        return new Employee(employeeV1Dto.getId(), employeeV1Dto.getName(), employeeV1Dto.getRole());
    }

    public Employee toEmployee(EmployeeV2Dto employeeV2Dto) {
        return new Employee(employeeV2Dto.getId(),
                employeeV2Dto.getFirstName() + " " + employeeV2Dto.getLastName(),
                employeeV2Dto.getRole());
    }
}
