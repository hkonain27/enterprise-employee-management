package com.hafsa.employees.service;

import com.hafsa.employees.model.Employee;
import com.hafsa.employees.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @Test
    void getByIdReturnsEmployee() {
        EmployeeRepository repo = mock(EmployeeRepository.class);
        Employee employee = new Employee();
        employee.setId("1");
        employee.setFirstName("Hafsa");

        when(repo.findById("1")).thenReturn(Optional.of(employee));

        EmployeeService service = new EmployeeService(repo);
        assertEquals("Hafsa", service.getById("1").getFirstName());
    }
}
