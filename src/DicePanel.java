import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Draws an actual dice face (rounded square + pips) instead of plain text,
 * and animates a quick "roll" by cycling random faces before landing on
 * the real result.
 */
public class DicePanel extends JPanel {
    private int face = 1;
    private final Random random = new Random();
    private Timer rollTimer;
    private boolean rolling = false;

    public DicePanel() {
        setPreferredSize(new Dimension(64, 64));
        setOpaque(false);
    }

    /** Animates through random faces, then calls onDone with the final result. */
    public void rollTo(int finalValue, Runnable onDone) {
        if (rolling) return;
        rolling = true;
        int[] ticksLeft = {10};
        rollTimer = new Timer(60, e -> {
            ticksLeft[0]--;
            if (ticksLeft[0] <= 0) {
                face = finalValue;
                rolling = false;
                rollTimer.stop();
                repaint();
                if (onDone != null) onDone.run();
            } else {
                face = 1 + random.nextInt(6);
                repaint();
            }
        });
        rollTimer.setRepeats(true);
        rollTimer.start();
    }

    public int getFace() {
        return face;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        Theme.enableAA(g2d);

        int size = Math.min(getWidth(), getHeight());
        int x = (getWidth() - size) / 2;
        int y = (getHeight() - size) / 2;
        int arc = size / 4;

        g2d.setColor(new Color(0, 0, 0, 70));
        g2d.fillRoundRect(x + 2, y + 3, size, size, arc, arc);
        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(x, y, size, size, arc, arc);
        g2d.setColor(Theme.NAVY);
        g2d.setStroke(new BasicStroke(2f));
        g2d.drawRoundRect(x, y, size, size, arc, arc);

        drawPips(g2d, x, y, size);
        g2d.dispose();
    }

    private void drawPips(Graphics2D g2d, int x, int y, int size) {
        g2d.setColor(Theme.NAVY);
        int r = size / 10;
        int pad = size / 4;
        int cx = x + size / 2, cy = y + size / 2;
        int left = x + pad, right = x + size - pad;
        int top = y + pad, bottom = y + size - pad;

        boolean[][] layout; // rows: top, middle, bottom; cols: left, center, right
        switch (face) {
            case 1: layout = new boolean[][]{{false,false,false},{false,true,false},{false,false,false}}; break;
            case 2: layout = new boolean[][]{{true,false,false},{false,false,false},{false,false,true}}; break;
            case 3: layout = new boolean[][]{{true,false,false},{false,true,false},{false,false,true}}; break;
            case 4: layout = new boolean[][]{{true,false,true},{false,false,false},{true,false,true}}; break;
            case 5: layout = new boolean[][]{{true,false,true},{false,true,false},{true,false,true}}; break;
            default: layout = new boolean[][]{{true,false,true},{true,false,true},{true,false,true}}; break;
        }

        int[] rowY = {top, cy, bottom};
        int[] colX = {left, cx, right};
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (layout[row][col]) {
                    g2d.fillOval(colX[col] - r, rowY[row] - r, r * 2, r * 2);
                }
            }
        }
    }
}
