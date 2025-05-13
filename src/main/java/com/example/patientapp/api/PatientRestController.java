package com.example.patientapp.api;

import com.example.patientapp.model.Patient;
import com.example.patientapp.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/patients") // API routes for patient management
@Tag(name = "Patient API", description = "Endpoints for managing patients")
public class PatientRestController {

    @Autowired
    private PatientService patientService;

    // Register a new patient (API request)
    @PostMapping("/register")
    @Operation(summary = "Register a new patient")
    public ResponseEntity<String> registerPatient(@Valid @RequestBody Patient patient) {
        try {
            patientService.registerPatient(patient);
            return ResponseEntity.status(HttpStatus.CREATED).body("Patient registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while registering the patient: " + e.getMessage());
        }
    }

    // Get patient by ID (API request)
    @GetMapping("/{id}")
    @Operation(summary = "Get patient details by ID")
    public ResponseEntity<Patient> getPatient(@PathVariable Long id) {
        Patient patient = patientService.getPatientById(id);
        if (patient != null) {
            return ResponseEntity.ok(patient);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Get a list of all patients (API request)
    @GetMapping
    @Operation(summary = "Get a list of all patients")
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        if (patients.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(patients);
        }
        return ResponseEntity.ok(patients);
    }
}