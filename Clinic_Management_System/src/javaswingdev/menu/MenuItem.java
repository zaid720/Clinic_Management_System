package javaswingdev.menu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class MenuItem extends JPanel {

    // قائمة المستمعين لأحداث اختيار القائمة
    private final List<EventMenuSelected> events = new ArrayList<>();

    // رقم العنصر في القائمة
    private final int index;

    // هل يحتوي العنصر على قائمة فرعية؟
    private final boolean hasSubMenu;

    // متحكم الرسوم المتحركة لفتح وغلق القائمة الفرعية
    private Animator animator;

    // زاوية السهم (للزر الذي يشير إلى وجود قائمة فرعية)
    private int buttonAngle = -1;

    // حالة فتح القائمة الفرعية (true مفتوحة - false مغلقة)
    private boolean open;

    // المُنشئ: يأخذ نموذج العنصر، رقم العنصر، وتخطيط MigLayout لإدارة التغييرات
    public MenuItem(ModelMenuItem item, int index, MigLayout layout) {
        this.index = index;
        this.hasSubMenu = item.getSubMenu().length > 0;
        initialize(item);
        if (hasSubMenu) {
            initializeAnimator(layout);
            buttonAngle = 1; // السهم يبدأ متجه لليمين (مغلق)
        }
    }

    // تهيئة عناصر القائمة للعنصر
    private void initialize(ModelMenuItem item) {
        setOpaque(false);
        // تحديد اتجاه المحتوى من اليمين إلى اليسار لواجهة عربية صحيحة
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        setForeground(new Color(170, 170, 170)); // اللون الرمادي للنص الافتراضي

        // تخطيط MigLayout مع تخصيص حجم للعناصر
        setLayout(new MigLayout("wrap,fillx,inset 0", "[fill]", "[fill,35!]" + (hasSubMenu ? "0[fill,30!]" : "")));

        // إنشاء العنصر الرئيسي للقائمة
        Item menu = new Item(true, 0);
        menu.setGoogleIcon(item.getIcon());
        menu.setText(item.getMenuName() + "  ");
        menu.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        menu.setHorizontalAlignment(SwingConstants.RIGHT); // محاذاة النص لليمين

        // تغيير اللون عند مرور الماوس فوق العنصر
        menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setForeground(menu.getMainColor());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!menu.isSelected()) {
                    setForeground(new Color(170, 170, 170));
                }
            }
        });

        // حدث الضغط على العنصر الأساسي
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runEvent(index, 0);
            }
        });

        // إذا كان هناك قائمة فرعية، اضف حدث فتح/غلق عند الضغط
        if (hasSubMenu) {
            menu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    open = !open;
                    startAnimator();
                }
            });
        }

        add(menu);

        // إنشاء وإضافة عناصر القائمة الفرعية مع دعم RTL
        int subIndex = 0;
        for (String subMenu : item.getSubMenu()) {
            Item sMenu = new Item(false, ++subIndex);
            sMenu.setText(subMenu + "  ");
            sMenu.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            sMenu.setHorizontalAlignment(SwingConstants.RIGHT);
            sMenu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    runEvent(index, sMenu.getIndex());
                }
            });
            add(sMenu);
        }
    }

    // تهيئة متحكم الرسوم المتحركة لفتح وإغلاق القائمة الفرعية
    private void initializeAnimator(MigLayout layout) {
        animator = new Animator(300, new TimingTargetAdapter() {
            private int height;

            @Override
            public void begin() {
                height = getPreferredSize().height - 35;
            }

            @Override
            public void timingEvent(float fraction) {
                float progress = open ? fraction : 1f - fraction;
                int heightNow = (int) (35 + progress * height);
                layout.setComponentConstraints(MenuItem.this, "h " + heightNow + "!");
                buttonAngle = (int) (progress * 180);
                revalidate();
                repaint();
            }
        });
        animator.setResolution(0);
        animator.setDeceleration(.5f);
        animator.setAcceleration(.5f);
    }

    // تشغيل أو إيقاف الرسوم المتحركة بناء على الحالة الحالية
    private void startAnimator() {
        if (animator.isRunning()) {
            float f = animator.getTimingFraction();
            animator.stop();
            animator.setStartFraction(1f - f);
        } else {
            animator.setStartFraction(0f);
        }
        animator.start();
    }

    // إضافة مستمع لأحداث اختيار عنصر في القائمة
    public void addEvent(EventMenuSelected event) {
        this.events.add(event);
    }

    // تنفيذ جميع الأحداث المسجلة عند اختيار عنصر
    private void runEvent(int index, int subIndex) {
        for (EventMenuSelected event : events) {
            event.menuSelected(index, subIndex);
        }
    }

    // إرجاع رقم العنصر
    public int getIndex() {
        return index;
    }

    // هل يحتوي العنصر على قائمة فرعية؟
    public boolean isHasSubMenu() {
        return hasSubMenu;
    }

    // إزالة التحديد من كافة العناصر
    public void clearSelected() {
        setForeground(new Color(170, 170, 170));
        for (Component comp : getComponents()) {
            Item item = (Item) comp;
            item.setSelected(false);
        }
    }

    // تعيين العنصر المحدد بناءً على رقم فرعي
    public void setSelectedIndex(int index) {
        for (Component comp : getComponents()) {
            Item item = (Item) comp;
            if (item.isMainMenu()) {
                item.setSelected(true);
                setForeground(item.getMainColor());
            }
            if (item.getIndex() == index) {
                item.setSelected(true);
                break;
            }
        }
    }

    // رسم سهم صغير يشير لحالة القائمة الفرعية (مفتوحة أو مغلقة)
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (buttonAngle >= 0) {
            Graphics2D g2 = (Graphics2D) g.create();
//            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getForeground());

            // إحداثيات السهم تكون على اليمين (لكن لأن اتجاه RTL، السهم سيكون ظاهرًا بشكل صحيح)
//            int x = getWidth() - 25;
            int x =  10;
            int y = 15;

            Path2D path = new Path2D.Double();
            path.moveTo(x, y);
            path.lineTo(x + 4, y + 4);
            path.lineTo(x + 8, y);

            AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(buttonAngle), x + 4, y + 2);
            g2.setStroke(new BasicStroke(1.8f));
            g2.draw(at.createTransformedShape(path));
            g2.dispose();
        }
    }
}
