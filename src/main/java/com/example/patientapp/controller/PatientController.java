package com.example.patientapp.controller;

import com.example.patientapp.model.Patient;
import com.example.patientapp.model.Prescription;
import com.example.patientapp.repository.PrescriptionRepository;
import com.example.patientapp.service.PatientService;
import com.example.patientapp.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class PatientController {

    @Autowired
    private PatientService patientService;
    @Autowired
    private PrescriptionService prescriptionService;
    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @GetMapping("/patients/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("patient", new Patient()); // Create a new Patient object and pass it to the view
        return "patient-registration"; // Thymeleaf page for patient registration
    }

   /* @PostMapping("/api/patients/register")
    public String registerPatient(@Valid @ModelAttribute Patient patient, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "patient-registration"; // Return to the registration page if there are validation errors
        }

        try {
            patientService.registerPatient(patient); // Save the patient through the service
            return "redirect:/dashboard"; // Redirect to the dashboard after registration

        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while registering the patient");
            return "patient-registration"; // Stay on the registration page in case of an error
        }
    }
*/
   @PostMapping("/api/patients/register")
   public String registerPatient(@Valid @ModelAttribute Patient patient,
                                 BindingResult bindingResult,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
       if (bindingResult.hasErrors()) {
           return "patient-registration";
       }

       try {
           patientService.registerPatient(patient);

           // ✅ Add success message
           redirectAttributes.addFlashAttribute("successMessage", "Patient registered successfully!");

           // ✅ Redirect to the registration form to show the message
           return "redirect:/patients/register";
       } catch (Exception e) {
           model.addAttribute("error", "An error occurred while registering the patient");
           return "patient-registration";
       }
   }



}
