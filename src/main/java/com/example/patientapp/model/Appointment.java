
package com.example.patientapp.model;

import jakarta.persistence.*;

@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String timeSlot;
    private boolean isBooked=false;

    @ManyToOne
    private Patient patient;  // Link to patient

    @ManyToOne
    private Doctor doctor;  // Add a link to the doctor for each appointment
    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL)
    /*private Prescription prescription;

    public Prescription getPrescription() {
        return prescription;
    }*/

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}