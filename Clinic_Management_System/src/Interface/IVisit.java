package Interface;

import Models.MVisit;
import java.util.ArrayList;
import personal.IPersonal;

public interface IVisit extends IPersonal<Integer, MVisit> {
    
    ArrayList<Integer> qeuryNumAppointment();

}
