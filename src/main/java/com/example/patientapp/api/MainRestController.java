package com.example.patientapp.api;



import com.example.patientapp.model.User;
import com.example.patientapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")  // API prefix for REST endpoints
@Tag(name = "Main API", description = "REST endpoints for user registration and access")
public class MainRestController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
        try {
            userService.save(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Registration failed: " + e.getMessage());
        }
    }

    @GetMapping("/dashboard")
    @Operation(summary = "Return dashboard data")
    public ResponseEntity<String> getDashboard() {
        // For now, just return a sample message
        return ResponseEntity.ok("Welcome to the dashboard!");
    }

    @GetMapping("/")
    @Operation(summary = "Home endpoint")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Welcome to the home page (API)");
    }

    @GetMapping("/login")
    @Operation(summary = "Login endpoint")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("Login endpoint - replace with JWT auth or session logic");
    }
}

