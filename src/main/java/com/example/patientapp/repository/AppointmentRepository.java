
package com.example.patientapp.repository;

import com.example.patientapp.model.Appointment;
//import com.example.patientapp.model.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByIsBookedFalse();
    List<Appointment> findByPatientIsNotNull();
    //List<Appointment> findByStatus(AppointmentStatus status);
    //Appointment findByAppointmentId(Long appointmentId);
    // Fetch available appointments
}
