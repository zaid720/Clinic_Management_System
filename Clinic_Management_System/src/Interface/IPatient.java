/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import Models.MPatient;
import Models.MUpcomingAppointment;
import java.util.ArrayList;
import personal.IPersonal;

/**
 *
 * @author ITS
 */
public interface IPatient extends IPersonal<Integer, MPatient> {
    
    ArrayList<MUpcomingAppointment> upcomingAppointments();
}
