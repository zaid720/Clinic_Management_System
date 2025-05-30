/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import Models.MPrescriptions;
import java.util.ArrayList;
import personal.IPersonal;

/**
 *
 * @author ITS
 */
public interface IPrescriptions extends IPersonal<Integer, MPrescriptions> {
    ArrayList<Integer> qeuryNumVisit();
}
