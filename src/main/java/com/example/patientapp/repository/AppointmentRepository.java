
package com.example.patientapp.repository;

import com.example.patientapp.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByIsBookedFalse();
    List<Appointment> findByPatientIsNotNull();

    // Fetch available appointments
}