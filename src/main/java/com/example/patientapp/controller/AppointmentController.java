package com.example.patientapp.controller;



import com.example.patientapp.model.*;
import com.example.patientapp.repository.*;
import com.example.patientapp.service.AppointmentService;
import com.example.patientapp.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    @Autowired
    private MedicineRepository medicineRepository;

    @GetMapping("/appointments")
    public String getAvailableAppointments(Model model) {
        // Fetch appointments that are not booked
        List<Appointment> availableAppointments = appointmentRepository.findByIsBookedFalse();

        // Check if there are available appointments
        boolean allBooked = availableAppointments.isEmpty();

        model.addAttribute("availableAppointments", availableAppointments);
        model.addAttribute("allBooked", allBooked);  // Indicate whether all appointments are booked

        return "appointments"; // Make sure this matches your view name
    }


    @GetMapping("/appointments/bookForm")
    public String showBookForm(@RequestParam("id") Long appointmentId, Model model) {
        List<Patient> patients = patientRepository.findAll();  // Fetch all patients
        List<Doctor> doctors = doctorRepository.findAll();  // Fetch all doctors
        //List<Medicine> medicines = medicineRepository.findAll();
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        model.addAttribute("appointmentId", appointmentId);
        model.addAttribute("patients", patients);  // Send patient list to view
        model.addAttribute("doctors", doctors);  // Send doctor list to view
        model.addAttribute("appointment", appointment);  // Add appointment info
       // model.addAttribute("medicines", medicines); // 🔥 Add this line
        model.addAttribute("isBooked", appointment.isBooked());  // Add flag for booking status
        return "book-appointment"; // Booking form view
    }
    /*@GetMapping("/prescriptions/add")
    public String showPrescriptionForm(@RequestParam("appointmentId") Long appointmentId, Model model) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        List<Medicine> medicines = medicineRepository.findAll();

        model.addAttribute("appointment", appointment);
        model.addAttribute("medicines", medicines);

        return "add-prescription";  // New Thymeleaf template
    }*/


    @PostMapping("/appointments/book")
    public String bookAppointment(@RequestParam("appointmentId") Long appointmentId,
                                  @RequestParam("patientId") Long patientId,
                                  @RequestParam("doctorId") Long doctorId,
                                 // @RequestParam(value = "medicineIds", required = false) List<Long> medicineIds

                                  RedirectAttributes redirectAttributes) {
        appointmentService.bookAppointment(appointmentId, patientId,doctorId);
        // Create a new prescription with the selected medicines
        //List<Medicine> medicines = medicineRepository.findAllById(medicineIds);
        /*Prescription prescription = new Prescription();
        prescription.setPatient(patientRepository.findById(patientId).orElseThrow());
        prescription.setDoctor(doctorRepository.findById(doctorId).orElseThrow());
        prescription.setAppointment(appointmentRepository.findById(appointmentId).orElseThrow());
        prescription.setMedicines(medicines);

        prescriptionRepository.save(prescription);*/
        redirectAttributes.addFlashAttribute("successMessage", "Appointment booked successfully!");


        return "redirect:/appointments";  // Important: reloads the appointment list
    }
    // Show the "Add Slot" form
    @GetMapping("/appointments/addSlotForm")
    public String showAddSlotForm() {
        return "add-appointment-slot";  // Updated template name to camelCase
    }

    // Handle adding new appointment slots
    @PostMapping("/appointments/addSlot")
    public String addAppointmentSlot(@RequestParam("timeSlot") String timeSlot,
                                     RedirectAttributes redirectAttributes) {
        Appointment appointment = new Appointment();
        appointment.setTimeSlot(timeSlot);
        appointment.setBooked(false);  // Ensure the new slot is not booked

        appointmentRepository.save(appointment);  // Save new appointment slot
        redirectAttributes.addFlashAttribute("successMessage", "New appointment slot added successfully!");
        return "redirect:/appointments";
    }
    @GetMapping("/appointments/completed")
    public String viewCompletedAppointments(Model model) {
        List<Appointment> completedAppointments = appointmentRepository.findByPatientIsNotNull();
        model.addAttribute("completedAppointments", completedAppointments);
        return "completed_appointments";
    }

}

