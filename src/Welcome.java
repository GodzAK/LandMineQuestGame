import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Welcome extends JFrame {
    public Welcome() {
        // Frame setup
        setTitle("Welcome to Land Mine Quest");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Godwin Dela Cruz\\Documents\\NetBeansProjects\\LandMine\\src\\logo2.png"));

        // Main panel
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        // Text area
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Serif", Font.PLAIN, 15));
        textArea.setForeground(Color.BLACK);
        textArea.setBackground(Color.WHITE);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setText("Introducing \"LandMineQuest\" – a thrilling game of strategy and knowledge where two players embark on a journey "
                + "to reach the coveted destination: 100. In this game, luck, wit, and quick thinking are key as players navigate a perilous"
                + " path littered with landmines, each one presenting a challenge to their intellect. As the players roll the dice, their fate"
                + " unfolds with each step they take. The board is marked with numbers, each representing a potential landing spot. But beware,"
                + " scattered throughout are treacherous landmines lying in wait. When a player lands on a landmine, they are confronted with a"
                + " question, testing their knowledge across various subjects. The rules are simple yet cunning – answer correctly, and you'll"
                + " leap forward, gaining an advantage by moving three steps ahead. However, a wrong answer brings consequences, "
                + "sending you tumbling back, the deduction leaving you strategizing your next move. Every turn is a gamble, every decision "
                + "critical. With each question answered, players inch closer to their goal, but the path is fraught with uncertainty. Will you"
                + " rise to the challenge, outsmart your opponent, and emerge victorious in the quest to conquer the landmines and reach the"
                + " pinnacle of 100? Only time will tell in this high-stakes game of LandMineQuest.");
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.NORTH);

        // Image panel
        BufferedImage image = loadImage("logo2.png");
        JPanel imagePanel = new JPanel(new GridBagLayout());
        imagePanel.setOpaque(false);
        if (image != null) {
            ImageIcon scaledIcon = new ImageIcon(image.getScaledInstance(500, 250, Image.SCALE_SMOOTH));
            imagePanel.add(new JLabel(scaledIcon));
        } else {
            imagePanel.add(new JLabel("Error loading image!", JLabel.CENTER));
        }
        panel.add(imagePanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);

        // Start button
        JButton startButton = new JButton("RULES");
        styleButton(startButton, new Color(60, 179, 113));
        startButton.addActionListener(e -> {
            playSound("mouseclick.wav");
            startGame();
        });
        buttonPanel.add(startButton);

        // Quit button
        JButton quitButton = new JButton("Quit");
        styleButton(quitButton, new Color(220, 20, 60));
        quitButton.addActionListener(e -> {
            playSound("mouseclick.wav");
            quitGame();
        });
        buttonPanel.add(quitButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        add(panel);
        setVisible(true);
    }

    private void styleButton(JButton button, Color color) {
        button.setPreferredSize(new Dimension(120, 40));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
    }

    private BufferedImage loadImage(String fileName) {
        try {
            URL url = getClass().getResource(fileName);
            return (url != null) ? ImageIO.read(url) : null;
        } catch (IOException e) {
            return null;
        }
    }

    private void playSound(String soundFileName) {
        try {
            URL url = getClass().getResource(soundFileName);
            if (url != null) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
            }
        } catch (Exception e) {
            // Error handling
        }
    }

    private void startGame() {
        new Menu();
        dispose();
    }

    private void quitGame() {
        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Welcome::new);
    }
}


