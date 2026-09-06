import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class Welcome extends JFrame {

    public Welcome() {
        setTitle("LandMineQuest");
        setSize(820, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setIconImage(loadIcon());

        BackdropPanel root = new BackdropPanel();
        root.setLayout(new GridBagLayout());
        setContentPane(root);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setOpaque(true);
        card.setBackground(Theme.PANEL);
        card.setBorder(BorderFactory.createEmptyBorder(28, 34, 28, 34));
        card.setMaximumSize(new Dimension(640, 560));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);

        BufferedImage logo = loadImage("logo2.png");
        if (logo != null) {
            ImageIcon icon = new ImageIcon(logo.getScaledInstance(220, 220, Image.SCALE_SMOOTH));
            JLabel logoLabel = new JLabel(icon);
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(logoLabel);
            card.add(Box.createRigidArea(new Dimension(0, 14)));
        }

        JLabel title = new JLabel("LandMineQuest");
        title.setFont(Theme.TITLE_FONT);
        title.setForeground(Theme.NAVY);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(title);
        card.add(Box.createRigidArea(new Dimension(0, 4)));

        JLabel subtitle = new JLabel("A trivia race across a minefield of Java questions");
        subtitle.setFont(Theme.BODY_FONT);
        subtitle.setForeground(Theme.TEXT_MUTED);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(subtitle);
        card.add(Box.createRigidArea(new Dimension(0, 18)));

        JTextArea blurb = new JTextArea(
                "Two players race from cell 1 to cell 100. Roll the dice each turn, and watch out "
                + "for landmine cells - land on one and you'll face a Java trivia question. Answer "
                + "correctly to leap 3 cells ahead, or get it wrong and slide 3 cells back. "
                + "First to reach 100 wins the quest!");
        blurb.setFont(Theme.BODY_FONT);
        blurb.setForeground(Theme.TEXT_DARK);
        blurb.setLineWrap(true);
        blurb.setWrapStyleWord(true);
        blurb.setOpaque(false);
        blurb.setEditable(false);
        blurb.setFocusable(false);
        blurb.setAlignmentX(Component.CENTER_ALIGNMENT);
        blurb.setMaximumSize(new Dimension(540, 130));
        card.add(blurb);
        card.add(Box.createRigidArea(new Dimension(0, 22)));

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        buttonRow.setOpaque(false);

        RoundedButton rulesButton = new RoundedButton("Rules & Start", Theme.INDIGO);
        rulesButton.addActionListener(e -> {
            SoundManager.play("mouseclick.wav");
            startGame();
        });
        buttonRow.add(rulesButton);

        RoundedButton quitButton = new RoundedButton("Quit", Theme.DANGER);
        quitButton.addActionListener(e -> {
            SoundManager.play("mouseclick.wav");
            quitGame();
        });
        buttonRow.add(quitButton);

        card.add(buttonRow);
        root.add(card);
        setVisible(true);
    }

    private void startGame() {
        new Menu();
        dispose();
    }

    private void quitGame() {
        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?",
                "Confirm Exit", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
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

    /** Panel that paints the navy-to-indigo gradient behind everything. */
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
        SwingUtilities.invokeLater(Welcome::new);
    }
}
