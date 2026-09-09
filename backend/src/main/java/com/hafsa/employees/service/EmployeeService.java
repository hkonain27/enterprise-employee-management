package com.hafsa.employees.service;

import com.hafsa.employees.exception.ResourceNotFoundException;
import com.hafsa.employees.model.Employee;
import com.hafsa.employees.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> getAll() {
        return repository.findAll();
    }

    public Employee getById(String id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id));
    }

    public Employee create(Employee employee) {
        repository.findByEmail(employee.getEmail()).ifPresent(e -> {
            throw new IllegalArgumentException("Employee email already exists");
        });
        return repository.save(employee);
    }

    public Employee update(String id, Employee incoming) {
        Employee current = getById(id);
        current.setFirstName(incoming.getFirstName());
        current.setLastName(incoming.getLastName());
        current.setEmail(incoming.getEmail());
        current.setDepartment(incoming.getDepartment());
        current.setJobTitle(incoming.getJobTitle());
        current.setLocation(incoming.getLocation());
        current.setManager(incoming.getManager());
        current.setEmploymentStatus(incoming.getEmploymentStatus());
        return repository.save(current);
    }

    public void delete(String id) {
        repository.delete(getById(id));
    }

    public List<Employee> search(String q) {
        return repository
            .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(q, q, q);
    }

    public List<Employee> byDepartment(String department) {
        return repository.findByDepartmentIgnoreCase(department);
    }
}
