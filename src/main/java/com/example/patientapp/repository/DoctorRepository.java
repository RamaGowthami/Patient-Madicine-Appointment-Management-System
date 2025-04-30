package com.example.patientapp.repository;

import com.example.patientapp.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findAll();  // Fetch all doctors
}
