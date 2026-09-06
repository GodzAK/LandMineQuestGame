import javax.swing.*;
import java.awt.*;

/**
 * The main gameplay window. Wires together the board, the dice, and the
 * question/win dialogs. Previously this logic lived in a class literally
 * named "Java" with everything static - split out and instance-based now
 * so it's easier to follow and extend.
 */
public class GameFrame extends JFrame {
    private static final int NUM_PLAYERS = BoardPanel.NUM_PLAYERS;

    private final String[] playerNames = new String[NUM_PLAYERS];
    private int currentPlayer = 0;

    private final BoardPanel boardPanel = new BoardPanel();
    private final DicePanel dicePanel = new DicePanel();
    private final JLabel turnBanner = new JLabel();
    private final RoundedButton rollButton = new RoundedButton("Roll Dice", Theme.GOLD);

    public GameFrame() {
        collectPlayerNames();

        setTitle("LandMineQuest");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Theme.NAVY_DARK);
        setContentPane(root);

        root.add(buildTopBar(), BorderLayout.NORTH);

        JPanel boardWrap = new JPanel(new GridBagLayout());
        boardWrap.setBackground(Theme.NAVY_DARK);
        boardWrap.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        boardWrap.add(boardPanel);
        root.add(boardWrap, BorderLayout.CENTER);

        rollButton.setForeground(Theme.TEXT_DARK);
        rollButton.addActionListener(e -> onRollClicked());
        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 14));
        bottomBar.setBackground(Theme.NAVY_DARK);
        bottomBar.add(rollButton);
        root.add(bottomBar, BorderLayout.SOUTH);

        updateTurnBanner();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel buildTopBar() {
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Theme.NAVY);
        top.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));

        turnBanner.setFont(Theme.BANNER_FONT);
        turnBanner.setForeground(Theme.TEXT_LIGHT);
        top.add(turnBanner, BorderLayout.WEST);

        JPanel dicePanelWrap = new JPanel();
        dicePanelWrap.setOpaque(false);
        dicePanelWrap.add(dicePanel);
        top.add(dicePanelWrap, BorderLayout.EAST);

        return top;
    }

    private void collectPlayerNames() {
        for (int i = 0; i < NUM_PLAYERS; i++) {
            String name = JOptionPane.showInputDialog(null, "Enter name for Player " + (i + 1) + ":");
            playerNames[i] = (name == null || name.trim().isEmpty()) ? "Player " + (i + 1) : name.trim();
        }
    }

    private void updateTurnBanner() {
        turnBanner.setText(playerNames[currentPlayer] + "'s turn");
        turnBanner.setForeground(currentPlayer == 0 ? Theme.PLAYER_ONE.brighter() : Theme.PLAYER_TWO.brighter());
    }

    private void onRollClicked() {
        rollButton.setEnabled(false);
        int result = 1 + (int) (Math.random() * 6);
        dicePanel.rollTo(result, () -> {
            int target = boardPanel.getPosition(currentPlayer) + result;
            boardPanel.animateMove(currentPlayer, target, this::afterDiceMove);
        });
    }

    private void afterDiceMove() {
        if (checkWinAndHandle()) return;

        int position = boardPanel.getPosition(currentPlayer);
        QuestionBank.Question question = QuestionBank.get(position);
        if (question != null) {
            SoundManager.play("landminesound.wav");
            QuestionDialog dialog = new QuestionDialog(this, currentPlayer + 1, question);
            boolean correct = dialog.askAndWait();
            int newTarget = position + (correct ? 3 : -3);
            boardPanel.animateMove(currentPlayer, newTarget, this::afterQuestionResolved);
        } else {
            endTurn();
        }
    }

    private void afterQuestionResolved() {
        if (checkWinAndHandle()) return;
        endTurn();
    }

    private void endTurn() {
        currentPlayer = (currentPlayer + 1) % NUM_PLAYERS;
        updateTurnBanner();
        rollButton.setEnabled(true);
    }

    /** Returns true if the game ended (win dialog was shown and handled). */
    private boolean checkWinAndHandle() {
        if (boardPanel.getPosition(currentPlayer) < BoardPanel.WINNING_POSITION) {
            return false;
        }
        WinDialog winDialog = new WinDialog(this, playerNames[currentPlayer]);
        boolean playAgain = winDialog.showAndWait();
        if (playAgain) {
            resetGame();
        } else {
            System.exit(0);
        }
        return true;
    }

    private void resetGame() {
        boardPanel.setPositionInstant(0, 1);
        boardPanel.setPositionInstant(1, 1);
        currentPlayer = 0;
        updateTurnBanner();
        rollButton.setEnabled(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameFrame::new);
    }
}
