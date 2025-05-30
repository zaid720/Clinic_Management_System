package com.raven.swing;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.ComponentOrientation;
import java.awt.Font;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;
import javax.swing.BorderFactory;

public class MyPasswordField extends JPasswordField {

    private Icon prefixIcon;
    private Icon suffixIcon;
    private String hint = "";

    public MyPasswordField() {
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(0, 0, 0, 0));
        setForeground(Color.decode("#7A8C8D"));
        setFont(new Font("sansserif", Font.PLAIN, 13));
        setSelectionColor(new Color(75, 175, 152));
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        // إعادة الرسم عند دخول وخروج التركيز
        addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                repaint();
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                repaint();
            }
        });
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
        repaint();
    }

    public Icon getPrefixIcon() {
        return prefixIcon;
    }

    public void setPrefixIcon(Icon prefixIcon) {
        this.prefixIcon = prefixIcon;
        updateBorder();
    }

    public Icon getSuffixIcon() {
        return suffixIcon;
    }

    public void setSuffixIcon(Icon suffixIcon) {
        this.suffixIcon = suffixIcon;
        updateBorder();
    }

    private void updateBorder() {
        int left = 15;
        int right = 15;

        if (suffixIcon != null) {
            left = suffixIcon.getIconWidth() + 15;
        }
        if (prefixIcon != null) {
            right = prefixIcon.getIconWidth() + 15;
        }

        setBorder(BorderFactory.createEmptyBorder(10, left, 10, right));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // خلفية مستديرة
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(230, 245, 241));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

        paintIcons(g2);
        super.paintComponent(g2);

        // رسم التلميح
        if (getPassword().length == 0 && !isFocusOwner() && hint != null && !hint.isEmpty()) {
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            FontMetrics fm = g2.getFontMetrics();
            Insets insets = getInsets();
            int x = getWidth() - insets.right - fm.stringWidth(hint);  // من اليمين
            int y = getHeight() / 2 + fm.getAscent() / 2 - 2;
            g2.setColor(new Color(180, 180, 180));
            g2.drawString(hint, x, y);
        }

        g2.dispose();
    }

    private void paintIcons(Graphics2D g2) {
        if (prefixIcon != null) {
            Image img = ((ImageIcon) prefixIcon).getImage();
            int y = (getHeight() - prefixIcon.getIconHeight()) / 2;
            int x = getWidth() - prefixIcon.getIconWidth() - 10;
            g2.drawImage(img, x, y, this);
        }

        if (suffixIcon != null) {
            Image img = ((ImageIcon) suffixIcon).getImage();
            int y = (getHeight() - suffixIcon.getIconHeight()) / 2;
            g2.drawImage(img, 10, y, this);
        }
    }
}