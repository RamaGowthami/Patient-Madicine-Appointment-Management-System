
/*package com.example.patientapp.model;

import jakarta.persistence.*;

@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String timeSlot;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)  // Make the patient mandatory
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id", nullable = false)  // Make the doctor mandatory
    private Doctor doctor;

    private boolean isBooked = false;  // Ensure default is 'false'

    // Default constructor required by JPA
    public Appointment() {
    }

    // Constructor for easy initialization
    public Appointment(String timeSlot, Patient patient, Doctor doctor) {
        this.timeSlot = timeSlot;
        this.patient = patient;
        this.doctor = doctor;
    }

    // Getter and Setter for isBooked
    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    // Getter and Setter methods
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

}*/

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
