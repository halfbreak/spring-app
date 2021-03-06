package com.hlbk.restapi.controllers;

import com.hlbk.restapi.converters.EmployeeConverter;
import com.hlbk.restapi.dtos.EmployeeV1Dto;
import com.hlbk.restapi.dtos.EmployeeV2Dto;
import com.hlbk.restapi.exceptions.EmployeeNotFoundException;
import com.hlbk.restapi.repositories.EmployeeRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
class EmployeeController {
    private final EmployeeConverter employeeConverter;
    private final EmployeeRepository repository;

    EmployeeController(EmployeeConverter employeeConverter, EmployeeRepository repository) {
        this.employeeConverter = employeeConverter;
        this.repository = repository;
    }

    @GetMapping(path = "/employees", produces = "application/springapp.api.v1+json")
    Flux<EmployeeV1Dto> all() {
        return Flux.fromIterable(repository.findAll())
                .map(employeeConverter::toEmployeeV1Dto);
    }

    @GetMapping(path = "/employees", produces = "application/springapp.api.v2+json")
    Flux<EmployeeV2Dto> allV2() {
        return Flux.fromIterable(repository.findAll())
                .map(employeeConverter::employeeV2Dto);
    }

    @PostMapping(path = "/employees",
            produces = "application/springapp.api.v1+json",
            consumes = "application/springapp.api.v1+json")
    Mono<EmployeeV1Dto> newEmployee(@RequestBody EmployeeV1Dto newEmployee) {
        return Mono.just(newEmployee)
                .map(employeeConverter::toEmployee)
                .map(repository::save)
                .map(employeeConverter::toEmployeeV1Dto);
    }

    @GetMapping(path = "/employees/{id}",
            produces = "application/springapp.api.v1+json")
    Mono<EmployeeV1Dto> one(@PathVariable Long id) {
        return Mono.justOrEmpty(repository.findById(id))
                .map(employeeConverter::toEmployeeV1Dto)
                .switchIfEmpty(Mono.error(new EmployeeNotFoundException(id)));
    }

    @PutMapping(path = "/employees/{id}",
            produces = "application/springapp.api.v1+json",
            consumes = "application/springapp.api.v1+json")
    Mono<EmployeeV1Dto> replaceEmployee(@RequestBody EmployeeV1Dto newEmployee, @PathVariable Long id) {
        return Mono.justOrEmpty(repository.findById(id))
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                })
                .map(employeeConverter::toEmployeeV1Dto)
                .switchIfEmpty(createNewIfNotExists(newEmployee, id));
    }

    private Mono<EmployeeV1Dto> createNewIfNotExists(EmployeeV1Dto newEmployee, Long id) {
        newEmployee.setId(id);
        return Mono.just(newEmployee).map(employeeConverter::toEmployee)
                .map(repository::save)
                .map(employeeConverter::toEmployeeV1Dto);
    }

    @DeleteMapping("/employees/{id}")
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
