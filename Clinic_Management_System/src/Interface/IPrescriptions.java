package Interface;

import Models.MPrescriptions;
import java.util.ArrayList;
import personal.IPersonal;

public interface IPrescriptions extends IPersonal<Integer, MPrescriptions> {
    ArrayList<Integer> qeuryNumVisit();
}
