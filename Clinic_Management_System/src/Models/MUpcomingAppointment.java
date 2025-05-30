/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.time.LocalDate;

/**
 *
 * @author ITS
 */
public class MUpcomingAppointment {

    private String name;
    private LocalDate appointment_date;

    public MUpcomingAppointment(String name, LocalDate appointment_date) {
        this.name = name;
        this.appointment_date = appointment_date;
    }

    public MUpcomingAppointment() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getAppointment_date() {
        return appointment_date;
    }

    public void setAppointment_date(LocalDate appointment_date) {
        this.appointment_date = appointment_date;
    }

}
