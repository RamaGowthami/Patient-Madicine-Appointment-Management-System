package com.example.patientapp.controller;

import com.example.patientapp.model.*;
import com.example.patientapp.repository.*;
import com.example.patientapp.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
@RequestMapping("/prescriptions")
public class PrescriptionController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;
@Autowired
private PrescriptionService prescriptionService;
    // Show Add Prescription Form
    @GetMapping("/add")
    public String showPrescriptionForm(@RequestParam("appointmentId") Long appointmentId, Model model) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid appointment ID: " + appointmentId));

        Prescription prescription = new Prescription();
        prescription.setAppointment(appointment); // Link to appointment

        List<Medicine> medicines = medicineRepository.findAll(); // For multi-select dropdown

        model.addAttribute("prescription", prescription);
        model.addAttribute("medicines", medicines);
        return "add-prescription";
    }


    @PostMapping("/save")
    public String savePrescription(@ModelAttribute Prescription prescription,
                                   @RequestParam("medicineIds") List<Long> medicineIds,
                                   RedirectAttributes redirectAttributes) {
        Long appointmentId = (prescription.getAppointment() != null) ? prescription.getAppointment().getId() : null;

        if (appointmentId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Appointment is required.");
            return "redirect:/appointments/completed";
        }

        if (medicineIds == null || medicineIds.isEmpty()) {
            redirectAttributes.addAttribute("error", "no-medicines");
            return "redirect:/prescriptions/add?appointmentId=" + appointmentId;
        }

        // Fetch real appointment entity and assign
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid appointment ID"));

        prescription.setAppointment(appointment);

        List<Medicine> selectedMeds = medicineRepository.findAllById(medicineIds);
        if (selectedMeds.size() != medicineIds.size()) {
            redirectAttributes.addAttribute("error", "invalid-medicines");
            return "redirect:/prescriptions/add?appointmentId=" + appointmentId;
        }

        prescription.setMedicines(selectedMeds);
        prescriptionRepository.save(prescription);

        redirectAttributes.addFlashAttribute("successMessage", "Prescription saved successfully!");
        return "redirect:/appointments/completed";
    }


    @GetMapping("/view")
    public String viewPrescription(@RequestParam("appointmentId") Long appointmentId, Model model) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);
        List<Medicine> medicines = medicineRepository.findAll();
        if (appointment != null) {
            Prescription prescription = prescriptionRepository.findByAppointmentId(appointmentId);
            if (prescription != null && !prescription.getMedicines().isEmpty()) {
                model.addAttribute("prescription", prescription);
                //model.addAttribute("medicines", medicines);
                //System.out.println("Medicines: " + prescription.getMedicines());
                System.out.println("Prescription: " + prescription);
                System.out.println("Medicines: " + (prescription != null ? prescription.getMedicines() : "null"));

            } else {
                model.addAttribute("error", "No medicines found for this prescription.");
            }
        } else {
            model.addAttribute("error", "Appointment not found");
        }
        return "view-prescription";
    }



    @GetMapping("/edit")  // not /prescriptions/edit
    public String showEditForm(@RequestParam("appointmentId") Long appointmentId, Model model) {
        Optional<Prescription> prescriptionOpt = prescriptionService.getPrescriptionByAppointmentId(appointmentId);

        if (prescriptionOpt.isPresent()) {
            Prescription prescription = prescriptionOpt.get();
            model.addAttribute("prescription", prescription);
            model.addAttribute("allMedicines", medicineRepository.findAll()); // for dropdown
            return "prescription-edit";
        } else {
            model.addAttribute("error", "Prescription not found");
            return "error";
        }
    }

    @PostMapping("/update")
    public String updatePrescription(@ModelAttribute Prescription prescription,
                                     @RequestParam("selectedMedicineIds") List<Long> selectedMedicineIds,
                                     RedirectAttributes redirectAttributes) {
        // Fetch the selected medicines from the database
        List<Medicine> selectedMedicines = medicineRepository.findAllById(selectedMedicineIds);

        // Set the medicines for the prescription
        prescription.setMedicines(selectedMedicines);

        // Save the updated prescription
        prescriptionRepository.save(prescription);

        // Add success message to redirect attributes
        redirectAttributes.addFlashAttribute("successMessage", "Prescription updated successfully!");

        // Redirect to the view page with the prescription's appointment ID
        return "redirect:/prescriptions/view?appointmentId=" + prescription.getAppointment().getId();
    }



   @GetMapping("/delete")
    public String deletePrescriptionByAppointmentId(@RequestParam("appointmentId") Long appointmentId,
                                                    RedirectAttributes redirectAttributes) {
        Prescription prescription = prescriptionRepository.findByAppointmentId(appointmentId);
        if (prescription != null) {
            prescriptionRepository.delete(prescription);
            redirectAttributes.addFlashAttribute("successMessage", "Prescription deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Prescription not found for the appointment!");
        }
        return "redirect:/appointments/completed";
    }

}
