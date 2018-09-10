package com.hlbk.restapi.repositories;

import com.hlbk.restapi.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
