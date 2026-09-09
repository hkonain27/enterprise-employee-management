package com.hafsa.employees.service;

import com.hafsa.employees.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssistantService {
    private final EmployeeService employees;

    public AssistantService(EmployeeService employees) {
        this.employees = employees;
    }

    public String answer(String question) {
        String q = question.toLowerCase();

        if (q.contains("how many") && q.contains("employee")) {
            return "There are " + employees.getAll().size() + " employees in the system.";
        }

        if (q.contains("engineering")) {
            List<Employee> matches = employees.byDepartment("Engineering");
            return matches.isEmpty()
                ? "No Engineering employees were found."
                : "Engineering has " + matches.size() + " employees: " +
                  matches.stream()
                    .map(e -> e.getFirstName() + " " + e.getLastName())
                    .toList();
        }

        return "I can currently answer basic employee-count and department questions. "
            + "A production version could connect an LLM to permission-checked backend tools.";
    }
}
