/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import Models.MUser;
import java.util.ArrayList;
import personal.IPersonal;

/**
 *
 * @author ITS
 */
public interface IUser extends IPersonal<Integer, MUser>{
    public ArrayList<MUser> qeuryLogin(String name);
}
