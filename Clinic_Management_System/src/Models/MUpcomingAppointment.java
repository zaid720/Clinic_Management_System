package Models;

import java.time.LocalDate;

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
