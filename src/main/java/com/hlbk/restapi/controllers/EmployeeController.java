package com.hlbk.restapi.controllers;

import com.hlbk.restapi.exceptions.EmployeeNotFoundException;
import com.hlbk.restapi.models.Employee;
import com.hlbk.restapi.repositories.EmployeeRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class EmployeeController {
    private final EmployeeRepository repository;

    EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/employees")
    Flux<Employee> all() {
        return Flux.fromIterable(repository.findAll());
    }

    @PostMapping("/employees")
    Mono<Employee> newEmployee(@RequestBody Employee newEmployee) {
        return Mono.just(repository.save(newEmployee));
    }

    @GetMapping("/employees/{id}")
    Mono<Employee> one(@PathVariable Long id) {
        return Mono.justOrEmpty(repository.findById(id))
                .switchIfEmpty(Mono.error(new EmployeeNotFoundException(id)));
    }

    @PutMapping("/employees/{id}")
    Mono<Employee> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

        return Mono.justOrEmpty(repository.findById(id))
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                })
                .defaultIfEmpty(createNewIfNotExists(newEmployee, id));
    }

    private Employee createNewIfNotExists(Employee newEmployee, Long id) {
        newEmployee.setId(id);
        return repository.save(newEmployee);
    }

    @DeleteMapping("/employees/{id}")
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
