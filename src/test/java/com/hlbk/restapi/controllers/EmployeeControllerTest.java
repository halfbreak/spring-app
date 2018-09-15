package com.hlbk.restapi.controllers;

import com.hlbk.restapi.RestApiApplication;
import com.hlbk.restapi.models.Employee;
import com.hlbk.restapi.repositories.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(EmployeeController.class)
@ContextConfiguration(classes = {RestApiApplication.class})
//@SpringBootTest
public class EmployeeControllerTest {

    @Autowired
    private WebTestClient web;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    public void shouldGetAllEmployees() {
        assertThat(web).isNotNull();

        Employee alex = new Employee("alex", "USER");
        List<Employee> allEmployees = Collections.singletonList(alex);
        when(employeeRepository.findAll()).thenReturn(allEmployees);

        web.get().uri("employees")
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json;charset=UTF-8")
                .expectBody();
    }
}