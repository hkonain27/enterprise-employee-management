package com.hafsa.employees.controller;

import com.hafsa.employees.dto.AuthResponse;
import com.hafsa.employees.dto.LoginRequest;
import com.hafsa.employees.dto.RegisterRequest;
import com.hafsa.employees.model.Role;
import com.hafsa.employees.model.User;
import com.hafsa.employees.repository.UserRepository;
import com.hafsa.employees.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public AuthController(
        UserRepository users,
        PasswordEncoder encoder,
        AuthenticationManager authenticationManager,
        JwtService jwtService,
        UserDetailsService userDetailsService
    ) {
        this.users = users;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest req) {
        if (users.existsByEmail(req.email())) {
            throw new IllegalArgumentException("Email already registered");
        }
        Role role = req.role() == null ? Role.EMPLOYEE : req.role();
        users.save(new User(req.name(), req.email(), encoder.encode(req.password()), role));
        UserDetails details = userDetailsService.loadUserByUsername(req.email());
        return new AuthResponse(jwtService.generateToken(details));
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest req) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.email(), req.password())
        );
        UserDetails details = userDetailsService.loadUserByUsername(req.email());
        return new AuthResponse(jwtService.generateToken(details));
    }
}
