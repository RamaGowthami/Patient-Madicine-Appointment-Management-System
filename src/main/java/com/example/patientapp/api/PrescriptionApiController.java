package com.example.patientapp.api;



import com.example.patientapp.model.Medicine;
import com.example.patientapp.model.Prescription;
import com.example.patientapp.repository.AppointmentRepository;
import com.example.patientapp.repository.MedicineRepository;
import com.example.patientapp.repository.PrescriptionRepository;
import com.example.patientapp.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionApiController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private PrescriptionService prescriptionService;

    // Get all prescriptions
    @GetMapping
    public ResponseEntity<List<Prescription>> getAllPrescriptions() {
        List<Prescription> prescriptions = prescriptionRepository.findAll();
        return new ResponseEntity<>(prescriptions, HttpStatus.OK);
    }

    // Get prescription by appointment ID
    @GetMapping("/view/{appointmentId}")
    public ResponseEntity<Prescription> getPrescriptionByAppointmentId(@PathVariable Long appointmentId) {
        Optional<Prescription> prescriptionOpt = prescriptionService.getPrescriptionByAppointmentId(appointmentId);
        return prescriptionOpt.map(prescription -> new ResponseEntity<>(prescription, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Add a prescription
    @PostMapping("/add")
    public ResponseEntity<Prescription> addPrescription(@RequestBody Prescription prescription) {
        if (prescription.getAppointment() == null || prescription.getMedicines().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // If no appointment or medicines
        }

        Prescription savedPrescription = prescriptionRepository.save(prescription);
        return new ResponseEntity<>(savedPrescription, HttpStatus.CREATED);
    }

    // Update prescription
    @PutMapping("/update/{id}")
    public ResponseEntity<Prescription> updatePrescription(@PathVariable Long id, @RequestBody Prescription prescription) {
        if (!prescriptionRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        prescription.setId(id);
        Prescription updatedPrescription = prescriptionRepository.save(prescription);
        return new ResponseEntity<>(updatedPrescription, HttpStatus.OK);
    }

    // Delete prescription
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePrescription(@PathVariable Long id) {
        if (!prescriptionRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        prescriptionRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Successful deletion
    }

    // Get all medicines
    @GetMapping("/medicines")
    public ResponseEntity<List<Medicine>> getAllMedicines() {
        List<Medicine> medicines = medicineRepository.findAll();
        return new ResponseEntity<>(medicines, HttpStatus.OK);
    }
}

