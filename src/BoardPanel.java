import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Set;
import javax.imageio.ImageIO;

/**
 * Draws the 10x10 board and animates player tokens sliding cell-by-cell
 * instead of teleporting, so a dice roll actually feels like movement.
 */
public class BoardPanel extends JPanel {
    public static final int BOARD_SIZE = 10;
    public static final int CELL_SIZE = 54;
    public static final int NUM_PLAYERS = 2;
    public static final int WINNING_POSITION = 100;

    private final int[] playerPositions = new int[NUM_PLAYERS];
    private final double[] playerX = new double[NUM_PLAYERS];
    private final double[] playerY = new double[NUM_PLAYERS];
    private final Set<Integer> minePositions = QuestionBank.minePositions();

    private BufferedImage mineImage;
    private BufferedImage flagImage;

    public BoardPanel() {
        int side = BOARD_SIZE * CELL_SIZE;
        setPreferredSize(new Dimension(side, side));
        loadImages();
        setPositionInstant(0, 1);
        setPositionInstant(1, 1);
    }

    private void loadImages() {
        mineImage = loadImage("landmine1.jpg");
        flagImage = loadImage("flag3.jpg");
    }

    private BufferedImage loadImage(String name) {
        try {
            URL url = getClass().getResource(name);
            if (url == null) url = getClass().getResource("/" + name);
            return url != null ? ImageIO.read(url) : null;
        } catch (IOException e) {
            return null;
        }
    }

    public int getPosition(int playerIndex) {
        return playerPositions[playerIndex];
    }

    /** Jumps a token straight to a cell with no animation (used on reset). */
    public void setPositionInstant(int playerIndex, int position) {
        playerPositions[playerIndex] = clamp(position);
        Point p = cellCenter(playerPositions[playerIndex]);
        playerX[playerIndex] = p.x;
        playerY[playerIndex] = p.y;
        repaint();
    }

    private int clamp(int position) {
        return Math.max(1, Math.min(WINNING_POSITION, position));
    }

    /** Animates a token moving one cell at a time from its current spot to targetPosition. */
    public void animateMove(int playerIndex, int targetPosition, Runnable onFinish) {
        int clampedTarget = clamp(targetPosition);
        animateSingleStep(playerIndex, playerPositions[playerIndex], clampedTarget, onFinish);
    }

    private void animateSingleStep(int playerIndex, int currentCell, int targetCell, Runnable onFinish) {
        if (currentCell == targetCell) {
            playerPositions[playerIndex] = currentCell;
            if (onFinish != null) onFinish.run();
            return;
        }
        int nextCell = currentCell + (targetCell > currentCell ? 1 : -1);
        Point start = cellCenter(currentCell);
        Point end = cellCenter(nextCell);
        int totalFrames = 7;
        int[] frame = {0};
        Timer stepTimer = new Timer(14, null);
        stepTimer.addActionListener(e -> {
            frame[0]++;
            double t = Math.min(1.0, frame[0] / (double) totalFrames);
            playerX[playerIndex] = start.x + (end.x - start.x) * t;
            playerY[playerIndex] = start.y + (end.y - start.y) * t;
            repaint();
            if (frame[0] >= totalFrames) {
                ((Timer) e.getSource()).stop();
                playerPositions[playerIndex] = nextCell;
                animateSingleStep(playerIndex, nextCell, targetCell, onFinish);
            }
        });
        stepTimer.start();
    }

    private Point cellCenter(int position) {
        int row = (position - 1) / BOARD_SIZE;
        int colInRow = (position - 1) % BOARD_SIZE;
        int col = (row % 2 == 0) ? colInRow : (BOARD_SIZE - 1 - colInRow);
        int x = col * CELL_SIZE + CELL_SIZE / 2;
        int y = (BOARD_SIZE - row - 1) * CELL_SIZE + CELL_SIZE / 2;
        return new Point(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Theme.enableAA(g2d);

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                int position = (row % 2 == 0)
                        ? row * BOARD_SIZE + col + 1
                        : row * BOARD_SIZE + (BOARD_SIZE - col - 1) + 1;
                int x = col * CELL_SIZE;
                int y = (BOARD_SIZE - row - 1) * CELL_SIZE;
                drawCell(g2d, x, y, position);
            }
        }

        for (int i = 0; i < NUM_PLAYERS; i++) {
            drawToken(g2d, i);
        }
    }

    private void drawCell(Graphics2D g2d, int x, int y, int position) {
        boolean isMine = minePositions.contains(position);
        boolean isGoal = position == WINNING_POSITION;
        boolean checker = ((position - 1) / BOARD_SIZE + (position - 1) % BOARD_SIZE) % 2 == 0;

        Color base = checker ? Theme.PANEL : new Color(0xE4, 0xE6, 0xF2);
        if (isMine) base = new Color(0xFB, 0xD9, 0xDC);
        if (isGoal) base = Theme.GOLD_SOFT;

        g2d.setColor(base);
        g2d.fillRect(x, y, CELL_SIZE, CELL_SIZE);

        if (isMine) {
            g2d.setColor(Theme.DANGER);
            g2d.setStroke(new BasicStroke(2f));
            g2d.drawRect(x + 1, y + 1, CELL_SIZE - 2, CELL_SIZE - 2);
            if (mineImage != null) {
                int s = CELL_SIZE - 22;
                g2d.drawImage(mineImage, x + (CELL_SIZE - s) / 2, y + (CELL_SIZE - s) / 2 - 3, s, s, this);
            }
        } else if (isGoal) {
            g2d.setColor(Theme.GOLD);
            g2d.setStroke(new BasicStroke(2f));
            g2d.drawRect(x + 1, y + 1, CELL_SIZE - 2, CELL_SIZE - 2);
            if (flagImage != null) {
                int s = CELL_SIZE - 14;
                g2d.drawImage(flagImage, x + (CELL_SIZE - s) / 2, y + (CELL_SIZE - s) / 2 - 3, s, s, this);
            }
        } else {
            g2d.setColor(new Color(0, 0, 0, 30));
            g2d.drawRect(x, y, CELL_SIZE, CELL_SIZE);
        }

        g2d.setFont(Theme.CELL_FONT);
        g2d.setColor(isMine ? Theme.DANGER : Theme.TEXT_MUTED);
        g2d.drawString(String.valueOf(position), x + 4, y + 13);
    }

    private void drawToken(Graphics2D g2d, int playerIndex) {
        Color color = playerIndex == 0 ? Theme.PLAYER_ONE : Theme.PLAYER_TWO;
        double x = playerX[playerIndex];
        double y = playerY[playerIndex];

        boolean overlapping = playerPositions[0] == playerPositions[1];
        double offsetX = 0, offsetY = 0;
        if (overlapping) {
            offsetX = playerIndex == 0 ? -8 : 8;
        }

        int r = 12;
        int cx = (int) (x + offsetX);
        int cy = (int) (y + offsetY);

        g2d.setColor(new Color(0, 0, 0, 70));
        g2d.fillOval(cx - r, cy - r + 3, r * 2, r * 2);
        g2d.setColor(color);
        g2d.fillOval(cx - r, cy - r, r * 2, r * 2);
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2f));
        g2d.drawOval(cx - r, cy - r, r * 2, r * 2);
        g2d.setFont(Theme.CELL_FONT);
        g2d.setColor(Color.WHITE);
        String label = "P" + (playerIndex + 1);
        FontMetrics fm = g2d.getFontMetrics();
        g2d.drawString(label, cx - fm.stringWidth(label) / 2, cy + fm.getAscent() / 2 - 2);
    }
}
