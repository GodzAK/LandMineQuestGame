import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Menu extends JFrame {
    public Menu() {
        // Frame setup
        setTitle("Land Mine Quest - Menu");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window
        setLocationRelativeTo(null); // Center the frame on the screen
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Godwin Dela Cruz\\Documents\\NetBeansProjects\\LandMine\\src\\resources\\logo2.png")); // Set window icon

        // Main panel setup
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding

        // Load and display image
        BufferedImage image = loadImage("logo2.png");
        if (image != null) {
            Image scaledImage = image.getScaledInstance(500, 250, Image.SCALE_SMOOTH); // Scale image
            JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
            JPanel imagePanel = new JPanel();
            imagePanel.add(imageLabel);
            mainPanel.add(imagePanel, BorderLayout.NORTH); // Add image to top
        } else {
            mainPanel.add(new JLabel("Error loading image!"), BorderLayout.NORTH); // Error message
        }

        // Rules panel setup
        JPanel rulesPanel = new JPanel();
        rulesPanel.setLayout(new BoxLayout(rulesPanel, BoxLayout.Y_AXIS));
        JLabel titleLabel = new JLabel("Game Rules:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea rulesText = new JTextArea(
            "Navigate through a grid-based board, aiming to be the first to reach the end while overcoming challenges and avoiding setbacks."
            + "Objective: Reach the end of the board first.\n"
            + "Setup: Two players start at position 1 on a grid.\n"
            + "Gameplay: Roll a die to move forward, encountering Java-related challenges on gray cells.\n"
            + "Gray Cell Challenges: Answer Java questions correctly to stay on the cell; incorrect answers result in penalties.\n"
            + "Winning: The first player to reach or exceed the final cell wins.\n"
            + "Rules: Fair play, completion of turns, handling multiple players on the same cell, and immediate victory upon reaching the final cell.\n"
            + "Enjoy the game and test your Java knowledge!"
        );
        rulesText.setFont(new Font("Arial", Font.PLAIN, 15));
        rulesText.setLineWrap(true);
        rulesText.setWrapStyleWord(true);
        rulesText.setOpaque(false);
        rulesText.setEditable(false);
        rulesText.setFocusable(false);

        rulesPanel.add(titleLabel);
        rulesPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Vertical space
        rulesPanel.add(rulesText);
        mainPanel.add(rulesPanel, BorderLayout.CENTER); // Add rules to center

        // Button panel setup
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // Padding at the top

        JButton startButton = new JButton("Start!"); // Start button
        styleButton(startButton, new Color(60, 179, 113));
        startButton.addActionListener(e -> {
            playSound("mouseclick.wav");
            openGame();
        });
        buttonPanel.add(startButton); // Add start button

        mainPanel.add(buttonPanel, BorderLayout.SOUTH); // Add button panel to bottom
        add(mainPanel); // Add main panel to frame
        setVisible(true); // Make frame visible
    }

    // Method to style buttons
    private void styleButton(JButton button, Color color) {
        button.setPreferredSize(new Dimension(200, 50));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
    }

    // Method to load image from resources
    private BufferedImage loadImage(String fileName) {
        try {
            URL url = getClass().getResource(fileName);
            return (url != null) ? ImageIO.read(url) : null;
        } catch (IOException e) {
            return null;
        }
    }

    // Method to play sound
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

    // Method to open the game
    private void openGame() {
        SwingUtilities.invokeLater(() -> new Java().main(new String[]{})); // Open game window
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Menu::new); // Run the application
    }
}



