package Interface;

import Models.MPatient;
import Models.MUpcomingAppointment;
import java.util.ArrayList;
import personal.IPersonal;

public interface IPatient extends IPersonal<Integer, MPatient> {
    
    ArrayList<MUpcomingAppointment> upcomingAppointments();
}
