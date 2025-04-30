package com.example.patientapp.api;



import com.example.patientapp.model.Appointment;
import com.example.patientapp.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@Tag(name = "Appointment API", description = "API for managing appointments")
public class AppointmentRestController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @GetMapping
    @Operation(
            summary = "Get all appointments",
            description = "Returns a list of all appointments",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get appointment by ID",
            description = "Returns a single appointment by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "404", description = "Appointment not found")
            }
    )
    public Appointment getAppointmentById(@PathVariable Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
    }
}
