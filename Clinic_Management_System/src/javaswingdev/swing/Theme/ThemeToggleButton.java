package javaswingdev.swing.Theme;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Window;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

public class ThemeToggleButton extends JButton {

    private static boolean isDark = false;

    public ThemeToggleButton() {
        super("تبديل الوضع");
        setFont(new Font("Arial", Font.PLAIN, 14));

        this.addActionListener(e -> {
            // الحصول على النافذة الأصلية التي تحتوي على هذا الزر
            Window window = SwingUtilities.getWindowAncestor(this);

            // حفظ خصائص جميع المكونات في النافذة قبل تبديل الثيم
            if (window != null) {
                saveComponentStyles(window);
            }

            // تبديل الثيم بين الداكن والفاتح
            isDark = !isDark;
            if (isDark) {
                FlatDarkLaf.setup();
            } else {
                FlatLightLaf.setup();
            }

            // تحديث واجهة المستخدم مع تطبيق الثيم الجديد
            if (window != null) {
                SwingUtilities.updateComponentTreeUI(window);

                // استعادة خصائص المكونات المحفوظة بعد التحديث
                restoreComponentStyles(window);
            }
        });
    }

    // دالة لحفظ خصائص الخط والحجم المفضل لكل مكون
    private void saveComponentStyles(Component component) {
        if (component instanceof JComponent jcomponent) {
            jcomponent.putClientProperty("savedFont", component.getFont());
            jcomponent.putClientProperty("savedPreferredSize", component.getPreferredSize());
        }

        if (component instanceof Container container) {
            for (Component child : container.getComponents()) {
                saveComponentStyles(child); // استدعاء دالة الحفظ بشكل متكرر لجميع المكونات الفرعية
            }
        }
    }

    // دالة لاستعادة الخصائص المحفوظة لكل مكون بعد تغيير الثيم
    private void restoreComponentStyles(Component component) {
        if (component instanceof JComponent jcomponent) {
            Font font = (Font) jcomponent.getClientProperty("savedFont");
            Dimension preferredSize = (Dimension) jcomponent.getClientProperty("savedPreferredSize");

            if (font != null) {
                component.setFont(font);
            }
            if (preferredSize != null) {
                component.setPreferredSize(preferredSize);
            }
        }

        if (component instanceof Container container) {
            for (Component child : container.getComponents()) {
                restoreComponentStyles(child); // استدعاء دالة الاستعادة بشكل متكرر لجميع المكونات الفرعية
            }
        }
    }
}
