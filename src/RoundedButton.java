import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/** A JButton painted as a rounded, gradient-filled pill with a hover glow. */
public class RoundedButton extends JButton {
    private final Color base;
    private final Color hover;
    private boolean hovered = false;

    public RoundedButton(String text, Color base) {
        super(text);
        this.base = base;
        this.hover = base.brighter();
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(Theme.TEXT_LIGHT);
        setFont(Theme.BUTTON_FONT);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(180, 46));
        addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { hovered = true; repaint(); }
            @Override public void mouseExited(MouseEvent e) { hovered = false; repaint(); }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        Theme.enableAA(g2d);
        int arc = getHeight();
        Color fill = hovered ? hover : base;
        if (!isEnabled()) fill = Color.GRAY;
        g2d.setColor(new Color(0, 0, 0, 50));
        g2d.fillRoundRect(2, 3, getWidth() - 4, getHeight() - 4, arc, arc);
        g2d.setColor(fill);
        g2d.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 4, arc, arc);
        g2d.dispose();
        super.paintComponent(g);
    }
}
