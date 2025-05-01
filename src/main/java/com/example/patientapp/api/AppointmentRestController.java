package com.example.patientapp.api;




import com.example.patientapp.model.Appointment;
import com.example.patientapp.repository.AppointmentRepository;
import com.example.patientapp.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@Tag(name = "Appointment API", description = "Endpoints for managing appointments")
public class AppointmentRestController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentService appointmentService;

    // ✅ Get available (not booked) appointments
    @GetMapping("/available")
    @Operation(summary = "Get available (not booked) appointments")
    public List<Appointment> getAvailableAppointments() {
        return appointmentRepository.findByIsBookedFalse();
    }

    // ✅ Get completed appointments (linked to a patient)
    @GetMapping("/completed")
    @Operation(summary = "Get appointments that have been completed (have patient info)")
    public List<Appointment> getCompletedAppointments() {
        return appointmentRepository.findByPatientIsNotNull();
    }

    // ✅ Book an appointment
    @PostMapping("/book")
    @Operation(summary = "Book an appointment by appointmentId, patientId, and doctorId")
    public String bookAppointment(
            @RequestParam Long appointmentId,
            @RequestParam Long patientId,
            @RequestParam Long doctorId
    ) {
        appointmentService.bookAppointment(appointmentId, patientId, doctorId);
        return "Appointment booked successfully!";
    }

    // ✅ Add a new appointment slot
    @PostMapping("/addSlot")
    @Operation(summary = "Add a new appointment slot with given time")
    public String addAppointmentSlot(@RequestParam String timeSlot) {
        Appointment appointment = new Appointment();
        appointment.setTimeSlot(timeSlot);
        appointment.setBooked(false);
        appointmentRepository.save(appointment);
        return "New appointment slot added successfully!";
    }
}

