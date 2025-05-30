package javaswingdev.menu;

import direction.Direction;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javaswingdev.GoogleMaterialDesignIcon;
import javaswingdev.swing.scroll.ScrollBar;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;

public class Menu extends JPanel {

    // المؤشر الحالي للعنصر المحدد (لم يتم تحديد شيء في البداية)
    private int index = -1;

    // قائمة المستمعين (listeners) لأحداث اختيار عنصر القائمة
    private final List<EventMenuSelected> events = new ArrayList<>();

    // تخطيط MigLayout لإدارة ترتيب عناصر القائمة
    private MigLayout menuLayout;

    // اللوحة التي تحتوي على عناصر القائمة
    private JPanel panelMenu;

    // المُنشئ: يهيء القائمة عند إنشاء الكائن
    public Menu() {
        init();
    }

    // تهيئة القائمة وعناصرها
    private void init() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // تطبيق اتجاه المكونات من اليمين إلى اليسار على كل المكونات داخل هذه اللوحة
        Direction.applyComponentOrientationRecursively(this, ComponentOrientation.RIGHT_TO_LEFT);

        // إنشاء شريط التمرير الخاص بالقائمة
        JScrollPane scroll = createScroll();

        // إنشاء لوحة القائمة (Panel) التي ستحتوي عناصر القائمة
        panelMenu = createPanelMenu();

        // تعيين محتوى شريط التمرير ليكون لوحة القائمة
        scroll.setViewportView(panelMenu);

        // جعل خلفية شريط التمرير شفافة
        scroll.getViewport().setOpaque(false);
        scroll.setViewportBorder(null);

        // إضافة شريط التمرير إلى اللوحة الرئيسية
        add(scroll);

        // إضافة عناوين وأيقونات وعناصر للقائمة بشكل تجريبي

        addTitle("الرئيسية"); // MAIN 
        addMenuItem(new ModelMenuItem(GoogleMaterialDesignIcon.DASHBOARD, "لوحة التحكم"));

        addTitle("المرضى"); // Patients
        addMenuItem(new ModelMenuItem(GoogleMaterialDesignIcon.LOCAL_HOSPITAL ,  "المرضى"));
        
        addTitle("المواعيد"); // Appointments
        addMenuItem(new ModelMenuItem(GoogleMaterialDesignIcon.ACCESS_TIME, "المواعيد"));
        
        addTitle("الزيارات"); // Visits
        addMenuItem(new ModelMenuItem(GoogleMaterialDesignIcon.VIEW_LIST, "الزيارات"));
        
        addTitle("الوصفات الطبية"); // Prescriptoins
        addMenuItem(new ModelMenuItem(GoogleMaterialDesignIcon.LOCAL_PHARMACY, "الوصفات الطبية"));
        
        addTitle("المستخدمين"); // Users
        addMenuItem(new ModelMenuItem(GoogleMaterialDesignIcon.PERSON, "المستخدمين"));
    }

    // إنشاء JScrollPane مع إعدادات مخصصة لشريط التمرير العمودي فقط
    private JScrollPane createScroll() {
        JScrollPane scroll = new JScrollPane();
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // استخدام ScrollBar المخصص لشريط التمرير العمودي
        scroll.setVerticalScrollBar(new ScrollBar());
        return scroll;
    }

    // إنشاء لوحة القائمة مع تخطيط MigLayout لإدارة ترتيب العناصر
    private JPanel createPanelMenu() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        menuLayout = new MigLayout("wrap,fillx,inset 0,gapy 0", "[fill]");
        panel.setLayout(menuLayout);
        return panel;
    }

    // إنشاء عنصر قائمة (MenuItem) من نموذج ModelMenuItem مع التعامل مع الأحداث
    private JPanel createMenuItem(ModelMenuItem item) {
        MenuItem menuItem = new MenuItem(item, ++index, menuLayout);

        // إضافة مستمع لحدث اختيار عنصر القائمة
        menuItem.addEvent(new EventMenuSelected() {
            @Override
            public void menuSelected(int index, int indexSubMenu) {
                // إذا لم يكن هناك قائمة فرعية أو تم اختيار عنصر فرعي غير العنصر الأساسي
                if (!menuItem.isHasSubMenu() || indexSubMenu != 0) {
                    clearSelected(); // إزالة التحديد من العناصر الأخرى
                    setSelectedIndex(index, indexSubMenu); // تعيين العنصر المحدد
                }
            }
        });
        return menuItem;
    }

    // تنفيذ جميع الأحداث المرتبطة باختيار عنصر
    private void runEvent(int index, int indexSubMenu) {
        for (EventMenuSelected event : events) {
            event.menuSelected(index, indexSubMenu);
        }
    }

    // إضافة عنصر جديد إلى لوحة القائمة
    public void addMenuItem(ModelMenuItem menu) {
        panelMenu.add(createMenuItem(menu), "h 35!");
    }

    // إضافة عنوان (ترويسة) داخل القائمة مع ضبط الهوامش حسب اتجاه النص
    public void addTitle(String title) {
        JLabel label = new JLabel(title);

        // ضبط الهوامش بحيث يكون الحشو مناسب للغة العربية (من اليمين 20 بكسل)
        label.setBorder(getComponentOrientation().isLeftToRight()
                ? new EmptyBorder(15, 20, 5, 5)
                : new EmptyBorder(15, 5, 5, 20));

        label.setFont(label.getFont().deriveFont(Font.BOLD));
        label.setForeground(new Color(170, 170, 170));
        panelMenu.add(label);
    }

    // إضافة مساحة فارغة داخل القائمة بحجم معين (لزيادة التباعد)
    public void addSpace(int size) {
        panelMenu.add(new JLabel(), "h " + size + "!");
    }

    // تعيين العنصر المحدد حسب المؤشرات المُعطاة
    public void setSelectedIndex(int index, int indexSubMenu) {
        for (Component com : panelMenu.getComponents()) {
            if (com instanceof MenuItem) {
                MenuItem item = (MenuItem) com;
                if (item.getIndex() == index) {
                    item.setSelectedIndex(indexSubMenu);
                    runEvent(index, indexSubMenu);
                    break;
                }
            }
        }
    }

    // إزالة التحديد من جميع عناصر القائمة
    public void clearSelected() {
        for (Component com : panelMenu.getComponents()) {
            if (com instanceof MenuItem) {
                MenuItem item = (MenuItem) com;
                item.clearSelected();
            }
        }
    }

    // إضافة مستمع (listener) لأحداث اختيار عناصر القائمة
    public void addEvent(EventMenuSelected event) {
        events.add(event);
    }
}
