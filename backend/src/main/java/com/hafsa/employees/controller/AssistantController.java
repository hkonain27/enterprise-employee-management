package com.hafsa.employees.controller;

import com.hafsa.employees.service.AssistantService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/assistant")
@CrossOrigin(origins = "http://localhost:4200")
public class AssistantController {
    private final AssistantService assistant;

    public AssistantController(AssistantService assistant) {
        this.assistant = assistant;
    }

    @GetMapping("/ask")
    @PreAuthorize("hasAnyRole('ADMIN','HR','EMPLOYEE')")
    public Map<String, String> ask(@RequestParam String q) {
        return Map.of("answer", assistant.answer(q));
    }
}
