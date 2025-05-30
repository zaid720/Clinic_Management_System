package javaswingdev.menu;

import direction.Direction;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import javaswingdev.GoogleMaterialDesignIcon;
import javaswingdev.GoogleMaterialIcon;
import javaswingdev.GradientType;
import javaswingdev.system.SystemColor;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class Item extends JButton {

    private Color mainColor = SystemColor.MAIN_COLOR_2;
    private final int index;
    private final boolean mainMenu;
    private GoogleMaterialDesignIcon icon;
    private boolean mouseEnter;
    private float alpha;
    private Animator animator;

    public Item(boolean mainMenu, int index) {
        this.mainMenu = mainMenu;
        this.index = index;
        init();
    }

    private void init() {
        setContentAreaFilled(false);
        setForeground(new Color(50, 50, 50));
        setHorizontalAlignment(getComponentOrientation().isLeftToRight() ? JButton.RIGHT : JButton.RIGHT);
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        // إعداد الحدود حسب إذا كان العنصر رئيسي أو فرعي
        if (mainMenu) {
            setBorder(getComponentOrientation().isLeftToRight()
                    ? new EmptyBorder(0, 20, 0, 0)
                    : new EmptyBorder(0, 0, 0, 20));
        } else {
            setBorder(getComponentOrientation().isLeftToRight()
                    ? new EmptyBorder(0, 51, 0, 0)
                    : new EmptyBorder(0, 0, 0, 51));
        }

        // إعداد المؤثرات عند المرور بالماوس
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setForeground(mainColor);
                setGoogleIcon(icon);
                if (!mainMenu) {
                    mouseEnter = true;
                    startAnimator();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!isSelected()) {
                    setForeground(new Color(50, 50, 50));
                    setGoogleIcon(icon);
                }
                if (!mainMenu) {
                    mouseEnter = false;
                    startAnimator();
                }
            }
        });

        // إعداد حركة المؤشر للعنصر الفرعي
        if (!mainMenu) {
            animator = new Animator(350, new TimingTargetAdapter() {
                @Override
                public void timingEvent(float fraction) {
                    alpha = mouseEnter ? fraction : 1f - fraction;
                    repaint();
                }
            });
            animator.setResolution(0);
            animator.setAcceleration(.5f);
            animator.setDeceleration(.5f);
        }
    }
    
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (!mainMenu) {
            int height = getHeight();
            int size = 6;
            int y = (height - size) / 2;
            int x = getComponentOrientation().isLeftToRight() ? 27 : getWidth() - 27 - size;

            g2.setColor(new Color(170, 170, 170));
            g2.drawOval(x, y, size, size);

            g2.setColor(mainColor);
            if (isSelected()) {
                alpha = 1;
            }
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.fillOval(x, y, size + 1, size + 1);
        } else {
            if (isSelected()) {
                g2.setPaint(new GradientPaint(0, 3, SystemColor.MAIN_COLOR_1, 3, getHeight() - 6, SystemColor.MAIN_COLOR_2));
                g2.fillRect(0, 3, 3, getHeight() - 6);
            }
        }

        g2.dispose();
    }

    public void setGoogleIcon(GoogleMaterialDesignIcon icon) {
        if (icon != null) {
            this.icon = icon;
            setIcon(new GoogleMaterialIcon(icon, GradientType.HORIZONTAL,
                    SystemColor.MAIN_COLOR_1, SystemColor.MAIN_COLOR_2, 19).toIcon());
        }
    }

    @Override
    public void setSelected(boolean b) {
        super.setSelected(b);
        if (b || mouseEnter) {
            setForeground(mainColor);
        } else {
            alpha = 0;
            setForeground(new Color(50, 50, 50));
        }
        setGoogleIcon(icon);
    }
    
    public Color getMainColor() {
        return mainColor;
    }

    public int getIndex() {
        return index;
    }

    public boolean isMainMenu() {
        return mainMenu;
    }
}
