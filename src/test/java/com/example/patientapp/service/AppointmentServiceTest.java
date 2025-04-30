/*package com.example.patientapp.service;

import com.example.patientapp.model.Appointment;
import com.example.patientapp.repository.AppointmentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AppointmentServiceTest {

    @MockBean
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentService appointmentService;

    @Test
    void testGetAllAppointments() {
        Appointment appt1 = new Appointment();
        appt1.setAppointmentTime(LocalDateTime.now());
        Appointment appt2 = new Appointment();
        appt2.setAppointmentTime(LocalDateTime.now().plusDays(1));

        List<Appointment> mockList = Arrays.asList(appt1, appt2);
        Mockito.when(appointmentRepository.findAll()).thenReturn(mockList);

        List<Appointment> result = appointmentService.getAllAppointments();
        assertEquals(2, result.size());
    }
}*/
package com.example.patientapp.service;

import com.example.patientapp.model.Appointment;
import com.example.patientapp.model.Patient;
import com.example.patientapp.model.Doctor;
import com.example.patientapp.repository.AppointmentRepository;
import com.example.patientapp.repository.PatientRepository;
import com.example.patientapp.repository.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AppointmentServiceTest {

    @InjectMocks
    private AppointmentService appointmentService;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAvailableAppointments() {
        List<Appointment> mockList = List.of(new Appointment(), new Appointment());
        when(appointmentRepository.findByIsBookedFalse()).thenReturn(mockList);

        List<Appointment> result = appointmentService.getAvailableAppointments();

        assertEquals(2, result.size());
        verify(appointmentRepository, times(1)).findByIsBookedFalse();
    }

    @Test
    public void testBookAppointment_Success() {
        Long appointmentId = 1L;
        Long patientId = 2L;
        Long doctorId = 3L;

        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        appointment.setBooked(false);

        Patient patient = new Patient();
        patient.setId(patientId);

        Doctor doctor = new Doctor();
        doctor.setId(doctorId);

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));
        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));

        appointmentService.bookAppointment(appointmentId, patientId, doctorId);

        assertTrue(appointment.isBooked());
        assertEquals(patient, appointment.getPatient());
        assertEquals(doctor, appointment.getDoctor());
        verify(appointmentRepository).save(appointment);
    }

    @Test
    public void testBookAppointment_AlreadyBooked() {
        Appointment appointment = new Appointment();
        appointment.setBooked(true); // Important: ensure it's marked as already booked

        when(appointmentRepository.findById(anyLong())).thenReturn(Optional.of(appointment));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                appointmentService.bookAppointment(1L, 1L, 1L));

        assertEquals("Appointment is already booked.", exception.getMessage());

        // Optional: verify patientRepository was never called
        verify(patientRepository, never()).findById(anyLong());
        verify(doctorRepository, never()).findById(anyLong());
    }


    @Test
    public void testBookAppointment_InvalidPatient() {
        Appointment appointment = new Appointment();
        appointment.setBooked(false);

        when(appointmentRepository.findById(anyLong())).thenReturn(Optional.of(appointment));
        when(patientRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                appointmentService.bookAppointment(1L, 99L, 1L));

        assertEquals("Patient not found", exception.getMessage());
    }

    @Test
    public void testGetAppointmentById_Success() {
        Appointment appointment = new Appointment();
        appointment.setId(1L);

        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));

        Appointment result = appointmentService.getAppointmentById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testGetAppointmentById_NotFound() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            appointmentService.getAppointmentById(1L);
        });
    }
}
