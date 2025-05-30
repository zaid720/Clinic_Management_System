/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import personal.Personal;

/**
 *
 * @author ITS
 */
public class MVisit extends Personal {

    private int numAppointment;
    private LocalDate visit_date, appointment_date;
    private String diagnosis, notes, appointment_status;

    public MVisit(int id, int numAppointment, LocalDate appointment_date, String appointment_status, LocalDate visit_date, String diagnosis, String notes, LocalDateTime created_at) {
        super(id, created_at);
        this.numAppointment = numAppointment;
        this.appointment_date = appointment_date;
        this.appointment_status = appointment_status;
        this.visit_date = visit_date;
        this.diagnosis = diagnosis;
        this.notes = notes;
    }

    public MVisit(int numAppointment, LocalDate appointment_date, String appointment_status, LocalDate visit_date, String diagnosis, String notes) {
        this.numAppointment = numAppointment;
        this.appointment_date = appointment_date;
        this.appointment_status = appointment_status;
        this.visit_date = visit_date;
        this.diagnosis = diagnosis;
        this.notes = notes;
    }

    public MVisit() {
    }

    public int getNumAppointment() {
        return numAppointment;
    }

    public void setNumAppointment(int numAppointment) {
        this.numAppointment = numAppointment;
    }
    
    

    public String getAppointment_status() {
        return appointment_status;
    }

    public void setAppointment_status(String appointment_status) {
        this.appointment_status = appointment_status;
    }

    public LocalDate getAppointment_date() {
        return appointment_date;
    }

    public void setAppointment_date(LocalDate appointment_date) {
        this.appointment_date = appointment_date;
    }

    public void setVisit_date(LocalDate visit_date) {
        this.visit_date = visit_date;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getVisit_date() {
        return visit_date;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getNotes() {
        return notes;
    }

}
