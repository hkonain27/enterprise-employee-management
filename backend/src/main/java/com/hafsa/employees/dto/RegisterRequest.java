package com.hafsa.employees.dto;

import com.hafsa.employees.model.Role;

public record RegisterRequest(String name, String email, String password, Role role) {}
