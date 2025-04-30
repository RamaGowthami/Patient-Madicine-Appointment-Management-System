package com.example.patientapp.service;

import com.example.patientapp.model.Prescription;
import com.example.patientapp.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
    public class PrescriptionService {

        @Autowired
        private PrescriptionRepository prescriptionRepository;


        public Optional<Prescription> getPrescriptionByAppointmentId(Long appointmentId) {
            return Optional.ofNullable(prescriptionRepository.findByAppointmentId(appointmentId));
        }

    }


