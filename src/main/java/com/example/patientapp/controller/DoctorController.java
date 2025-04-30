package com.example.patientapp.controller;

import com.example.patientapp.model.Doctor;
import com.example.patientapp.repository.DoctorRepository;
import org.springframework.stereotype.Controller;
import com.example.patientapp.model.Appointment;
import com.example.patientapp.model.Patient;
import com.example.patientapp.repository.AppointmentRepository;
import com.example.patientapp.repository.PatientRepository;
import com.example.patientapp.service.AppointmentService;
import com.example.patientapp.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping("/doctors")
    public String getDoctors(Model model) {
        model.addAttribute("doctors", doctorRepository.findAll());
        return "doctors";  // View to show the list of doctors
    }

    @PostMapping("/doctors/add")
    public String addDoctor(@RequestParam("name") String name,
                            @RequestParam("specialization") String specialization) {
        Doctor doctor = new Doctor();
        doctor.setName(name);
        doctor.setSpecialization(specialization);
        doctorRepository.save(doctor);
        return "redirect:/doctors";  // Redirect to doctor list page after adding
    }
}




