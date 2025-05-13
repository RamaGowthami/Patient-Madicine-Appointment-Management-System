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
        List<Appointment> allAppointments = appointmentRepository.findAll();  // ✅ Changed
        boolean allBooked = allAppointments.stream().allMatch(Appointment::isBooked);

        model.addAttribute("availableAppointments", allAppointments);  // ✅ Changed
        model.addAttribute("allBooked", allBooked);
        return "appointments";
    }


    @GetMapping("/appointments/cancel")
    public String cancelAppointment(@RequestParam("id") Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid appointment ID"));

        appointment.setBooked(false); // Mark the appointment as not booked
        appointment.setPatient(null); // Optionally, set the patient to null if the appointment is cancelled

        appointmentRepository.save(appointment);

        return "redirect:/appointments?cancelled=true";
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


    /*@PostMapping("/appointments/book")
    public String bookAppointment(@RequestParam("appointmentId") Long appointmentId,
                                  @RequestParam("patientId") Long patientId,
                                  @RequestParam("doctorId") Long doctorId,
                                  // @RequestParam(value = "medicineIds", required = false) List<Long> medicineIds

                                  RedirectAttributes redirectAttributes) {
        appointmentService.bookAppointment(appointmentId, patientId,doctorId);
        // Create a new prescription with the selected medicines
        //List<Medicine> medicines = medicineRepository.findAllById(medicineIds);

        redirectAttributes.addFlashAttribute("successMessage", "Appointment booked successfully!");


        return "redirect:/appointments";  // Important: reloads the appointment list
    }*/
    @PostMapping("/appointments/book")
    public String bookAppointment(@RequestParam("appointmentId") Long appointmentId,
                                  @RequestParam("patientId") Long patientId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid appointment ID"));

        if (appointment.isBooked()) {
            return "redirect:/appointments?error=alreadyBooked";
        }

        // Set the patient for the appointment
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid patient ID"));
        appointment.setPatient(patient);

        // Mark the appointment as booked
        appointment.setBooked(true);

        // Save the appointment
        appointmentRepository.save(appointment);

        return "redirect:/appointments?success=true";
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