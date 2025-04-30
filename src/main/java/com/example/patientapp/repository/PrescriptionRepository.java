package com.example.patientapp.repository;


import com.example.patientapp.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findByAppointment_Patient_Id(Long patientId);

    // Custom query to fetch prescription by appointmentId
    Prescription findByAppointmentId(Long appointmentId);

}

