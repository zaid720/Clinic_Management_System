package direction;

import java.awt.Component;
import java.awt.ComponentOrientation;

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
