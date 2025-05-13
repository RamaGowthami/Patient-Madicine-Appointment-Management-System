package com.example.patientapp.controller;

import com.example.patientapp.model.Patient;
import com.example.patientapp.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    // Show patient registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "patient-registration"; // Thymeleaf page for patient registration
    }

    // Handle form submission for registering a patient
    @PostMapping("/register")
    public String registerPatient(@Valid @ModelAttribute Patient patient,
                                  BindingResult bindingResult,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "patient-registration";
        }

        try {
            patientService.registerPatient(patient);
            redirectAttributes.addFlashAttribute("successMessage", "Patient registered successfully!");
            return "redirect:/patients/register"; // Redirect back to the registration form
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while registering the patient");
            return "patient-registration";
        }
    }
}
