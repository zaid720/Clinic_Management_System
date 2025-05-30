/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package direction;

import java.awt.Component;
import java.awt.ComponentOrientation;

/**
 *
 * @author ITS
 */
public class Direction {
    
    public static void applyComponentOrientationRecursively(Component comp, ComponentOrientation orientation) {
        comp.setComponentOrientation(orientation);
        if (comp instanceof java.awt.Container) {
            for (Component child : ((java.awt.Container) comp).getComponents()) {
                applyComponentOrientationRecursively(child, orientation);
            }
        }
    }
    
}
