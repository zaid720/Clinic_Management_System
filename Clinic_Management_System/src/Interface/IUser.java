package Interface;

import Models.MUser;
import java.util.ArrayList;
import personal.IPersonal;

public interface IUser extends IPersonal<Integer, MUser>{
    public ArrayList<MUser> qeuryLogin(String name);
}
