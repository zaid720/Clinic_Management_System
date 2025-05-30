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
public class MPatient extends Personal {

    private int age;
    private String full_name, gender, address, medicat, phone;
    private boolean Active;
//    private LocalDateTime created_at;

    public MPatient(int patient_id, String full_name, int age, String gender, String phone, String address, String medicat, LocalDateTime created_at) {
        super(patient_id, created_at);
        this.age = age;
        this.full_name = full_name;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.medicat = medicat;
    }

    public MPatient(int age, String full_name, String gender, String phone, String address, String medicat) {
        this.age = age;
        this.full_name = full_name;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.medicat = medicat;
    }

    public MPatient() {
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean Active) {
        this.Active = Active;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMedicat(String medicat) {
        this.medicat = medicat;
    }

    public int getAge() {
        return age;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getMedicat() {
        return medicat;
    }
}
