package com.hlbk.restapi.advices;

import com.hlbk.restapi.exceptions.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Flux;

@ControllerAdvice
public class EmployeeNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Flux<String> employeeNotFoundHandler(EmployeeNotFoundException ex) {
        return Flux.just(ex.getMessage());
    }
}
