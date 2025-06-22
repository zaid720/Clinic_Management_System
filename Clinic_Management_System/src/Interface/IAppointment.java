package Interface;

import Models.MAppointment;
import Models.MQeuryName;
import java.util.ArrayList;
import personal.IPersonal;

public interface IAppointment extends IPersonal<Integer, MAppointment> {
    ArrayList<MQeuryName> namePatientQeury();
    ArrayList<MQeuryName> nameDoctorQeury();
}
