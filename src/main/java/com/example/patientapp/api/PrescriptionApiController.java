package com.example.patientapp.api;



import com.example.patientapp.model.Prescription;
import com.example.patientapp.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionApiController {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    // Get Prescription by Appointment ID
    @GetMapping("/by-appointment/{appointmentId}")
    public Prescription getPrescriptionByAppointmentId(@PathVariable Long appointmentId) {
        return prescriptionRepository.findByAppointmentId(appointmentId);
    }


}

