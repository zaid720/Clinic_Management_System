package Models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import personal.Personal;

public class MAppointment extends Personal {

    private LocalDate appointment_date;
    private LocalTime appointment_time;
    private String status, patient_name, doctor_name;
    private int idPatitent, idDoctor;
    private boolean active;

    public MAppointment(int id, String patient_name, String doctor_name, LocalDate appointment_date, LocalTime appointment_time, String status, LocalDateTime created_at) {
        super(id, created_at);
        this.patient_name = patient_name;
        this.doctor_name = doctor_name;
        this.appointment_date = appointment_date;
        this.appointment_time = appointment_time;
        this.status = status;
    }

    public MAppointment(String patient_name, String doctor_name, LocalDate appointment_date, LocalTime appointment_time, String status) {
        this.patient_name = patient_name;
        this.doctor_name = doctor_name;
        this.appointment_date = appointment_date;
        this.appointment_time = appointment_time;
        this.status = status;
    }

    public MAppointment() {
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getIdPatitent() {
        return idPatitent;
    }

    public void setIdPatitent(int idPatitent) {
        this.idPatitent = idPatitent;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public void setAppointment_date(LocalDate appointment_date) {
        this.appointment_date = appointment_date;
    }

    public void setAppointment_time(LocalTime appointment_time) {
        this.appointment_time = appointment_time;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getAppointment_date() {
        return appointment_date;
    }

    public LocalTime getAppointment_time() {
        return appointment_time;
    }

    public String getStatus() {
        return status;
    }

}
