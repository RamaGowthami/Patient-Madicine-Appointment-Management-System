package com.example.patientapp.api;



import com.example.patientapp.model.Doctor;
import com.example.patientapp.repository.DoctorRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@Tag(name = "Doctor API", description = "Endpoints for managing doctors")
public class DoctorRestController {

    @Autowired
    private DoctorRepository doctorRepository;

    // ✅ Get a list of all doctors
    @GetMapping
    @Operation(summary = "Get a list of all doctors")
    public List<Doctor> getDoctors() {
        return doctorRepository.findAll();
    }

    // ✅ Add a new doctor
    @PostMapping("/add")
    @Operation(summary = "Add a new doctor with name and specialization")
    public String addDoctor(
            @RequestParam("name") String name,
            @RequestParam("specialization") String specialization
    ) {
        Doctor doctor = new Doctor();
        doctor.setName(name);
        doctor.setSpecialization(specialization);
        doctorRepository.save(doctor);
        return "Doctor added successfully!";
    }
}

