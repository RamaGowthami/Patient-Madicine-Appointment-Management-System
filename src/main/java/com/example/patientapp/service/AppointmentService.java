/*package com.example.patientapp.service;
import com.example.patientapp.model.Appointment;
import com.example.patientapp.model.Patient;
import com.example.patientapp.repository.AppointmentRepository;
import com.example.patientapp.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    public List<Appointment> getAvailableAppointments() {
        return appointmentRepository.findByIsBookedFalse();
    }
    public List<Appointment> getAvailableAppointmentsWithDoctor() {
        return appointmentRepository.findByIsBookedFalse();
    }


    public void bookAppointment(Long appointmentId, Long patientId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (appointment.isBooked()) {
            throw new RuntimeException("Appointment is already booked.");
        }

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        appointment.setBooked(true);
        appointment.setPatient(patient);
        appointmentRepository.save(appointment);
    }*/
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

    public List<Appointment> getAvailableAppointments() {
        return appointmentRepository.findByIsBookedFalse();
    }

    public void bookAppointment(Long appointmentId, Long patientId, Long doctorId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
// Check booking status FIRST
        if (appointment.isBooked()) {
            throw new RuntimeException("Appointment is already booked.");
        }
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        appointment.setBooked(true);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);  // Set the doctor for the appointment
        appointmentRepository.save(appointment);
    }
    public Appointment getAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid appointment ID"));
    }
}
