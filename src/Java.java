import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
public class Java {
  
   static final int BOARD_SIZE = 10; // Size of the game board
   static final int CELL_SIZE = 50; // Size of each cell
   static final int NUM_PLAYERS = 2; // Number of players
   static final int WINNING_POSITION = 100; // Position to win the game
   static int[] playerPositions = new int[NUM_PLAYERS]; // Positions of players
   static int currentPlayer = 0; // Current player (0 for Player 1, 1 for Player 2)
   static int diceResult = 1; // Initial value of the dice
   static Set<Integer> grayMarkPositions = new HashSet<>(Arrays.asList(44, 7, 84, 25, 59, 91, 38, 18, 66, 74, 5, 29, 64, 33,
           11, 23, 40, 45, 55, 70, 78, 89, 95, 99));  // Positions to mark in gray
   static Map<Integer, String[]> questionMap = new HashMap<>(); // Map to associate gray numbers with questions and choices
   static Map<Integer, String> correctAnswerMap = new HashMap<>(); // Map to associate gray numbers with correct answers
   static {
   	questionMap.put(44, new String[]{"What is the purpose of a constructor in Java?", "A) To initialize class variables", "B) To create objects", "C) To allocate memory", "D) To define class methods"});
   	correctAnswerMap.put(44, "B) To create objects");
   	
   	questionMap.put(7, new String[]{"Which of the following is used to achieve multiple inheritance in Java?", "A) Interfaces", "B) Abstract classes", "C) Inheritance", "D) Polymorphism"});
   	correctAnswerMap.put(7, "A) Interfaces");
   	
   	questionMap.put(84, new String[]{"What is the difference between == and .equals() method in Java?", "A) == compares object references, while .equals() compares object content.", "B) == compares object content, while .equals() compares object references.", "C) Both == and .equals() compare object references.", "D) Both == and .equals() compare object content."});
   	correctAnswerMap.put(84, "A) == compares object references, while .equals() compares object content.");
   	
   	questionMap.put(25, new String[]{"Which keyword is used to prevent a method from being overridden in Java?", "A) final", "B) static", "C) abstract", "D) private"});
   	correctAnswerMap.put(25, "A) final");
   	
   	questionMap.put(59, new String[]{"What does the static keyword signify when applied to a method in Java?", "A) It means the method belongs to the class rather than any specific instance.", "B) It means the method can only be accessed by other static methods.", "C) It means the method cannot be overridden.", "D) It means the method can only be called from the same package."});
   	correctAnswerMap.put(59, "A) It means the method belongs to the class rather than any specific instance.");
   	
   	questionMap.put(91, new String[]{"In Java, what is the purpose of the super keyword?", "A) To call the superclass constructor", "B) To access superclass methods and variables", "C) To override superclass methods", "D) To create subclass instances"});
   	correctAnswerMap.put(91, "B) To access superclass methods and variables");
   	
   	questionMap.put(38, new String[]{"What is the difference between throw and throws in Java exception handling?", "A) throw is used to propagate an exception, while throws is used to declare an exception.", "B) throws is used to propagate an exception, while throw is used to declare an exception.", "C) Both throw and throws are used to declare exceptions.", "D) Both throw and throws are used to propagate exceptions."});
   	correctAnswerMap.put(38, "A) throw is used to propagate an exception, while throws is used to declare an exception.");
   	
   	questionMap.put(18, new String[]{"What is the purpose of the this keyword in Java?", "A) To refer to the current class instance", "B) To refer to the superclass instance", "C) To create a new class instance", "D) To initialize class variables"});
   	correctAnswerMap.put(18, "A) To refer to the current class instance");
   	
   	questionMap.put(66, new String[]{"What is the default value of an instance variable in Java if it is not explicitly initialized?", "A) 0", "B) null", "C) false", "D) It depends on the data type"});
   	correctAnswerMap.put(66, "D) It depends on the data type");
   	
   	questionMap.put(74, new String[]{"Which of the following collections in Java does not allow duplicate elements?", "A) ArrayList", "B) LinkedList", "C) HashSet", "D) HashMap"});
   	correctAnswerMap.put(74, "C) HashSet");
   	
   	questionMap.put(5, new String[]{"What is method overloading in Java?", "A) Providing multiple definitions of the same method in a class.", "B) Creating methods with the same name but different return types.", "C) Creating methods with the same name but different parameters.", "D) Creating methods with the same parameters but different names."});
   	correctAnswerMap.put(5, "C) Creating methods with the same name but different parameters.");
   	
   	questionMap.put(29, new String[]{"How are exceptions handled in Java?", "A) By using the finally block", "B) By using the try, catch, and finally blocks", "C) By using the assert keyword", "D) By using the switch statement"});
   	correctAnswerMap.put(29, "B) By using the try, catch, and finally blocks");
   	
   	questionMap.put(64, new String[]{"What is the purpose of the default keyword in Java interfaces?", "A) To specify a default value for a variable", "B) To specify a default constructor for a class", "C) To specify a default implementation for a method in an interface", "D) To specify a default access modifier for class members"});
   	correctAnswerMap.put(64, "C) To specify a default implementation for a method in an interface");
   	
   	questionMap.put(33, new String[]{"What is the difference between == and equals() method for comparing objects in Java?", "A) There is no difference, both are used for reference comparison.", "B) == compares references, while equals() compares values.", "C) == compares values, while equals() compares references.", "D) == and equals() are both used for value comparison."});
   	correctAnswerMap.put(33, "B) == compares references, while equals() compares values.");
   	
   	questionMap.put(11, new String[]{"What is the purpose of the volatile keyword in Java?", "A) To indicate that a variable may be modified asynchronously.", "B) To prevent a variable from being modified.", "C) To synchronize access to a variable.", "D) To declare a variable constant."});
   	correctAnswerMap.put(11, "A) To indicate that a variable may be modified asynchronously.");
   	
   	questionMap.put(23, new String[]{"What is a lambda expression in Java?", "A) A way to represent anonymous methods", "B) A type of exception handling mechanism", "C) A data structure used for storing key-value pairs", "D) A method that takes another method as an argument"});
   	correctAnswerMap.put(23, "A) A way to represent anonymous methods");
   	
   	questionMap.put(40, new String[]{"What does the break statement do in Java?", "A) Exits the current loop or switch statement.", "B) Continues with the next iteration of the loop.", "C) Jumps to a specific label in the code.", "D) Throws an exception."});
   	correctAnswerMap.put(40, "A) Exits the current loop or switch statement.");
   	
   	questionMap.put(45, new String[]{"What is the purpose of the transient keyword in Java?", "A) To prevent a variable from being serialized.", "B) To indicate that a variable should be serialized.", "C) To prevent a variable from being accessed outside its class.", "D) To indicate that a variable should be accessed outside its class."});
   	correctAnswerMap.put(45, "A) To prevent a variable from being serialized.");
   	questionMap.put(55, new String[]{"Which collection class in Java provides a way to access its elements by their index?", "A) Set", "B) Map", "C) List", "D) Queue"});
   	correctAnswerMap.put(55, "C) List");
   	questionMap.put(70, new String[]{"What is the purpose of the finalize() method in Java?", "A) To explicitly deallocate resources before an object is garbage collected.", "B) To perform cleanup operations before an object is garbage collected.", "C) To check if an object is eligible for garbage collection.", "D) To prevent an object from being garbage collected."});
   	correctAnswerMap.put(70, "B) To perform cleanup operations before an object is garbage collected.");
   	
   	questionMap.put(78, new String[]{"What is the purpose of the new keyword in Java?", "A) To create a new instance of a class.", "B) To allocate memory for a variable.", "C) To declare a new variable.", "D) To initialize a variable with a default value."});
   	correctAnswerMap.put(78, "A) To create a new instance of a class.");
   	
   	questionMap.put(89, new String[]{"What is the purpose of the static block in Java?", "A) To initialize static variables of a class.", "B) To initialize instance variables of a class.", "C) To initialize the superclass of a class.", "D) To declare static methods of a class."});
   	correctAnswerMap.put(89, "A) To initialize static variables of a class.");
   	
   	questionMap.put(95, new String[]{"What is the difference between the StringBuilder and StringBuffer classes in Java?", "A) StringBuilder is synchronized, while StringBuffer is not.", "B) StringBuffer is synchronized, while StringBuilder is not.", "C) Both StringBuilder and StringBuffer are synchronized.", "D) Neither StringBuilder nor StringBuffer is synchronized."});
   	correctAnswerMap.put(95, "B) StringBuffer is synchronized, while StringBuilder is not.");
   	
   	questionMap.put(99, new String[]{"Which keyword is used to prevent a method from being overridden in Java?", "A) final", "B) static", "C) abstract", "D) private"});
   	correctAnswerMap.put(99, "A) final");
   }
   public static void main(String[] args) {
       playerPositions[0] = 1; // Player 1 starts at cell 1
       playerPositions[1] = 1; // Player 2 starts at cell 1
       // Prompt for player names
       String[] playerNames = new String[NUM_PLAYERS];
       for (int i = 0; i < NUM_PLAYERS; i++) {
       playerNames[i] = JOptionPane.showInputDialog(null, "Enter name for Player " + (i + 1) + ":");
       if (playerNames[i] == null || playerNames[i].trim().isEmpty()) {
       playerNames[i] = "Player " + (i + 1); // Default name if none provided
       }
   }
       SwingUtilities.invokeLater(() -> {
           JFrame frame = new JFrame("Java Quiz Game");
           frame.setSize(800, 600); // Set the same size as Menu and Welcome
           frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           GameBoardPanel gameBoardPanel = new GameBoardPanel();
           frame.add(gameBoardPanel, BorderLayout.CENTER);
           frame.setResizable(false);
          
          
           // Set the window icon
           frame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Godwin Dela Cruz\\eclipse-workspace\\LandMine\\resources\\logo2.png"));
           // Add dice panel to the top of the frame
           JPanel dicePanel = new JPanel();
           JLabel diceLabel = new JLabel();
           updateDiceLabel(diceLabel);
           dicePanel.add(diceLabel);
           // Add roll dice button
           JButton rollButton = new JButton("Roll Dice");
           rollButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        rollDice();
        updateDiceLabel(diceLabel);
        playerPositions[currentPlayer] += diceResult; // Move player by dice roll
        gameBoardPanel.repaint(); // Repaint the board
        System.out.println("Player " + (currentPlayer + 1) + " rolled a " + diceResult +
                " and moved to cell " + playerPositions[currentPlayer]);
        // Check if the player has won the game
        if (playerPositions[currentPlayer] >= WINNING_POSITION) {
            String winnerName = playerNames[currentPlayer];
            String message = winnerName + " wins the game! Congratulations!";
            String[] options = {"Play Again", "Exit"};
            int choice = JOptionPane.showOptionDialog(null, message, "Game Over", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            if (choice == 0) {
                // Reset game and start again
                resetGame();
                gameBoardPanel.repaint(); // Repaint the board
            } else {
                // Exit the game
                System.exit(0);
            }
        }
        // Check if the player landed on a gray cell
        if (grayMarkPositions.contains(playerPositions[currentPlayer])) {
            askQuestion(playerPositions[currentPlayer], currentPlayer, gameBoardPanel);
        } else {
            // Switch to the next player only if no question was asked
            currentPlayer = (currentPlayer + 1) % NUM_PLAYERS;
        }
    }
});


           dicePanel.add(rollButton);
           frame.add(dicePanel, BorderLayout.NORTH);
           frame.pack();
           frame.setLocationRelativeTo(null); // Center the frame on the screen
           frame.setVisible(true);
       });
   }
   // Method to reset the game state
   static void resetGame() {
       playerPositions[0] = 1; // Player 1 starts at cell 1
       playerPositions[1] = 1; // Player 2 starts at cell 1
       currentPlayer = 0; // Reset current player to Player 1
       diceResult = 1; // Reset dice result
   }
                      
                         
                  
// Utility method to play sound
  
   private AudioInputStream loadAudioStream(String landminesound) {
       AudioInputStream audioInputStream = null;
       try {
           audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("resources\\landminesound.wav"));
           if (audioInputStream == null) {
               System.out.println("Resource not found: " + "resources\\landminesound.wav");
           }
       } catch (IOException | UnsupportedAudioFileException e) {
           e.printStackTrace();
           System.out.println("Error loading audio stream: " + e.getMessage());
       }
       return audioInputStream;
   }
   static void playSound(String landminesound) {
       try {
           File url = new File(landminesound);
           AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
           Clip clip = AudioSystem.getClip();
           clip.open(audioInputStream);
           clip.start();
       } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
           e.printStackTrace();
       }
   }
  
  
   // Method to roll the dice
   static void rollDice() {
       diceResult = new Random().nextInt(6) + 1; // Generate a random number between 1 and 6
   }
   // Method to update the dice label with the current dice result
   static void updateDiceLabel(JLabel label) {
       label.setText("Dice: " + diceResult);
   }
// Method to ask a question
static void askQuestion(int position, int player, GameBoardPanel gameBoardPanel) {
    // Play sound when landing on a gray cell (landmine)
    playSound("C:\\Users\\Godwin Dela Cruz\\eclipse-workspace\\LandMine\\src\\landminesound.wav");

    String[] questionData = questionMap.get(position);
    String correctAnswer = correctAnswerMap.get(position);
    if (questionData != null && correctAnswer != null) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel questionLabel = new JLabel(questionData[0]);
        panel.add(questionLabel);
        ButtonGroup group = new ButtonGroup();
        JRadioButton[] options = new JRadioButton[4];
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton(questionData[i + 1]);
            group.add(options[i]);
            panel.add(options[i]);
        }

        int result;
        do {
            result = JOptionPane.showOptionDialog(null, panel, "Player " + (player + 1) + " Question",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    new Object[]{ "Submit" }, "Submit");
        } while (result == JOptionPane.CLOSED_OPTION); // Loop until the user selects "Submit"

        // Check which option was selected
        for (JRadioButton option : options) {
            if (option.isSelected()) {
                if (option.getText().equals(correctAnswer)) {
                    JOptionPane.showMessageDialog(null, "Correct! Move forward 3 cells.");
                    playerPositions[player] += 3; // Move forward 3 cells for correct answer
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect. The correct answer is: " + correctAnswer + ". Move back 3 cells.");
                    playerPositions[player] -= 3; // Move back 3 cells for incorrect answer
                }
                break;
            }
        }

        // Ensure player position is within bounds
        playerPositions[player] = Math.max(1, playerPositions[player]);
        playerPositions[player] = Math.min(WINNING_POSITION, playerPositions[player]);

        // Check if the player has won the game
        if (playerPositions[currentPlayer] >= WINNING_POSITION) {
            String[] playerNames = null;
            String winnerName = playerNames[currentPlayer];
            JOptionPane.showMessageDialog(null, winnerName + " wins!");
            System.exit(0); // Exit the game
        }

        gameBoardPanel.repaint(); // Repaint the board
        // Switch to the next player after answering the question
        currentPlayer = (currentPlayer + 1) % NUM_PLAYERS;
    }
}




   // Custom panel to draw the game board
   static class GameBoardPanel extends JPanel {
   private Map<Integer, Image> grayCellImages = new HashMap<>();
   private ImageIcon flagImage;
   public GameBoardPanel() {
       setPreferredSize(new Dimension(BOARD_SIZE * CELL_SIZE, BOARD_SIZE * CELL_SIZE));
       setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
       loadImages();
   }
   private void loadImages() {
       try {
           // Load the gray cell image
           Image grayImage = new ImageIcon(getClass().getResource("/landmine1.jpg")).getImage();
           for (int pos : grayMarkPositions) {
               grayCellImages.put(pos, grayImage);
           }
          
           // Load the flag image
           flagImage = new ImageIcon(getClass().getResource("/flag3.jpg"));
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
   @Override
   protected void paintComponent(Graphics g) {
       super.paintComponent(g);
       Graphics2D g2d = (Graphics2D) g;
       for (int row = 0; row < BOARD_SIZE; row++) {
           for (int col = 0; col < BOARD_SIZE; col++) {
               int position;
               if (row % 2 == 0) {
                   position = row * BOARD_SIZE + col + 1; // Left to right
               } else {
                   position = row * BOARD_SIZE + (BOARD_SIZE - col - 1) + 1; // Right to left
               }
               int x = col * CELL_SIZE;
               int y = (BOARD_SIZE - row - 1) * CELL_SIZE;
               // Draw cell background
               g2d.setColor(Color.WHITE);
               g2d.fillRect(x, y, CELL_SIZE, CELL_SIZE);
               // Draw cell border
               g2d.setColor(Color.BLACK);
               g2d.drawRect(x, y, CELL_SIZE, CELL_SIZE);
               // Draw cell number
               g2d.setColor(Color.BLACK);
               g2d.drawString(String.valueOf(position), x + 10, y + 20);
               // Draw gray marked cells with an image
               if (grayMarkPositions.contains(position)) {
                   g2d.setColor(Color.GRAY);
                   g2d.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                   g2d.drawImage(grayCellImages.get(position), x + 5, y + 5, CELL_SIZE - 10, CELL_SIZE - 10, this);
               }
               // Draw flag image on position 100
               if (position == WINNING_POSITION) {
                   g2d.drawImage(flagImage.getImage(), x + 5, y + 5, CELL_SIZE - 10, CELL_SIZE - 10, this);
               }
           }
       }
       // Draw players
       for (int i = 0; i < NUM_PLAYERS; i++) {
           int position = playerPositions[i];
           int row = (position - 1) / BOARD_SIZE;
           int col = (position - 1) % BOARD_SIZE;
           if (row % 2 == 1) {
               col = BOARD_SIZE - col - 1; // Adjust column for snake and ladder pattern
           }
           int x = col * CELL_SIZE + CELL_SIZE / 2;
           int y = (BOARD_SIZE - row - 1) * CELL_SIZE + CELL_SIZE / 2;
           // Set color based on player
           if (i == 0) {
               g2d.setColor(Color.RED); // Player 1 color
           } else {
               g2d.setColor(Color.BLUE); // Player 2 color
           }
           // If both players are in the same cell, draw them slightly offset
           if (playerPositions[0] == playerPositions[1] && playerPositions[0] == position) {
               if (i == 0) {
                   g2d.fillOval(x - 15, y - 15, 20, 20); // Player 1 offset, smaller size
               } else {
                   g2d.fillOval(x + 4, y - 15, 20, 20); // Player 2 offset, smaller size
               }
           } else {
               g2d.fillOval(x - 8, y - 15, 20, 20); // Normal draw, smaller size
           }
       }
   }
}
}

