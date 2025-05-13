
package com.example.patientapp.service;

import com.example.patientapp.model.Appointment;
import com.example.patientapp.model.Patient;
import com.example.patientapp.model.Doctor;
import com.example.patientapp.repository.AppointmentRepository;
import com.example.patientapp.repository.PatientRepository;
import com.example.patientapp.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    // Get all available appointments (not booked)
    public List<Appointment> getAvailableAppointments() {
        return appointmentRepository.findByIsBookedFalse();
    }

    // Book an appointment
    public void bookAppointment(Long appointmentId, Long patientId, Long doctorId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (appointment.isBooked()) {
            throw new RuntimeException("Appointment is already booked.");
        }

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        appointment.setBooked(true); // Mark appointment as booked
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointmentRepository.save(appointment);
    }


    // Get a specific appointment by ID
    public Appointment getAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid appointment ID"));
    }

    // Cancel an appointment
    public void cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        // Clear the appointment details
        appointment.setPatient(null);
        appointment.setDoctor(null);
        appointment.setBooked(false);  // Mark the appointment as unbooked

        appointmentRepository.save(appointment);
    }

}
