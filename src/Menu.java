import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class Menu extends JFrame {

    public Menu() {
        setTitle("LandMineQuest - Rules");
        setSize(820, 640);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setIconImage(loadIcon());

        BackdropPanel root = new BackdropPanel();
        root.setLayout(new GridBagLayout());
        setContentPane(root);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Theme.PANEL);
        card.setBorder(BorderFactory.createEmptyBorder(26, 32, 26, 32));
        card.setMaximumSize(new Dimension(640, 560));

        JLabel title = new JLabel("Game Rules");
        title.setFont(Theme.TITLE_FONT);
        title.setForeground(Theme.NAVY);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(title);
        card.add(Box.createRigidArea(new Dimension(0, 4)));

        JLabel goldRule = new JLabel("Reach cell 100 first to win the quest");
        goldRule.setFont(Theme.BODY_BOLD);
        goldRule.setForeground(Theme.INDIGO);
        goldRule.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(goldRule);
        card.add(Box.createRigidArea(new Dimension(0, 18)));

        card.add(ruleRow("1", "Setup", "Two players start at cell 1 on a 10x10 board."));
        card.add(ruleRow("2", "Roll", "Take turns rolling the dice to move forward."));
        card.add(ruleRow("3", "Landmines", "Gray cells hold a Java trivia question."));
        card.add(ruleRow("4", "Correct answer", "Move forward 3 extra cells."));
        card.add(ruleRow("5", "Wrong answer", "Move back 3 cells."));
        card.add(ruleRow("6", "Win", "First to reach or pass cell 100 wins."));

        card.add(Box.createRigidArea(new Dimension(0, 22)));

        RoundedButton startButton = new RoundedButton("Start Game", Theme.GOLD);
        startButton.setForeground(Theme.TEXT_DARK);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(e -> {
            SoundManager.play("mouseclick.wav");
            openGame();
        });
        card.add(startButton);

        root.add(card);
        setVisible(true);
    }

    private JPanel ruleRow(String number, String heading, String body) {
        JPanel row = new JPanel(new BorderLayout(14, 0));
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setMaximumSize(new Dimension(560, 50));
        row.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));

        JLabel badge = new JLabel(number, SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                Theme.enableAA(g2d);
                g2d.setColor(Theme.INDIGO);
                g2d.fillOval(0, 0, getWidth(), getHeight());
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        badge.setForeground(Color.WHITE);
        badge.setFont(Theme.BODY_BOLD);
        badge.setPreferredSize(new Dimension(28, 28));
        row.add(badge, BorderLayout.WEST);

        JPanel textCol = new JPanel();
        textCol.setOpaque(false);
        textCol.setLayout(new BoxLayout(textCol, BoxLayout.Y_AXIS));
        JLabel h = new JLabel(heading);
        h.setFont(Theme.BODY_BOLD);
        h.setForeground(Theme.TEXT_DARK);
        JLabel b = new JLabel(body);
        b.setFont(Theme.BODY_FONT);
        b.setForeground(Theme.TEXT_MUTED);
        textCol.add(h);
        textCol.add(b);
        row.add(textCol, BorderLayout.CENTER);

        return row;
    }

    private void openGame() {
        SwingUtilities.invokeLater(GameFrame::new);
        dispose();
    }

    private BufferedImage loadImage(String fileName) {
        try {
            URL url = getClass().getResource(fileName);
            return (url != null) ? ImageIO.read(url) : null;
        } catch (IOException e) {
            return null;
        }
    }

    private Image loadIcon() {
        BufferedImage img = loadImage("logo2.png");
        return img != null ? img : new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    }

    private static class BackdropPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            Theme.enableAA(g2d);
            g2d.setPaint(Theme.backdrop(getWidth(), getHeight()));
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Menu::new);
    }
}
