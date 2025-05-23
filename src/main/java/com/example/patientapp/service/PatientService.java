/*package com.example.patientapp.service;

import com.example.patientapp.model.Patient;
import com.example.patientapp.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public void registerPatient(Patient patient) {
        // Save the patient to the database
        patientRepository.save(patient);
    }

    public Patient findPatientById(Long patientId) {
        // Fetch the patient by ID from the repository
        return patientRepository.findById(patientId).orElse(null); // return null if not found
    }
}
*/
package com.example.patientapp.service;

import com.example.patientapp.model.Patient;
import com.example.patientapp.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient findPatientById(Long patientId) {
        return patientRepository.findById(patientId).orElse(null); // Fetch the patient by ID
    }
    public void registerPatient(Patient patient) {
        // Save the patient to the database
        patientRepository.save(patient);
    }
    // Method to get a patient by ID for rest Controller
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + id));
    }
    // Method to get all patients
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();  // Fetch all patients from the repository
    }

}
