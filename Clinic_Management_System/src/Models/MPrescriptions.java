/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.time.LocalDateTime;
import personal.Personal;

/**
 *
 * @author ITS
 */
public class MPrescriptions extends Personal{
    
    private int NumVisit;
    private String medication, dosage, duration;

    public MPrescriptions(Integer id, int NumVisit, String medication, String dosage, String duration, LocalDateTime created_at) {
        super(id, created_at);
        this.NumVisit = NumVisit;
        this.medication = medication;
        this.dosage = dosage;
        this.duration = duration;
    }

    public MPrescriptions(int NumVisit, String medication, String dosage, String duration) {
        this.NumVisit = NumVisit;
        this.medication = medication;
        this.dosage = dosage;
        this.duration = duration;
    }

    public MPrescriptions() {
    }

    public int getNumVisit() {
        return NumVisit;
    }

    public void setNumVisit(int NumVisit) {
        this.NumVisit = NumVisit;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
    
    
    
}
