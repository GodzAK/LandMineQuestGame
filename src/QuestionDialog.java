import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Styled modal dialog for a landmine trivia question. Shows the question,
 * lets the player pick one of four answer cards, then reveals a colored
 * correct/incorrect result inline before auto-closing.
 */
public class QuestionDialog extends JDialog {

    private boolean correct = false;
    private int selectedIndex = -1;
    private final List<AnswerCard> cards = new ArrayList<>();
    private final QuestionBank.Question question;
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel stack = new JPanel();
    private RoundedButton submitButton;

    public QuestionDialog(Frame owner, int playerNumber, QuestionBank.Question question) {
        super(owner, "Landmine!", true);
        this.question = question;
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setSize(480, 420);
        setLocationRelativeTo(owner);
        buildUI(playerNumber);
    }

    private void buildUI(int playerNumber) {
        stack.setLayout(cardLayout);
        stack.add(buildQuestionPanel(playerNumber), "question");
        stack.add(buildResultPanel(true), "correct");
        stack.add(buildResultPanel(false), "incorrect");
        setContentPane(stack);
        cardLayout.show(stack, "question");
    }

    private JPanel buildQuestionPanel(int playerNumber) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Theme.PANEL);

        JPanel header = new JPanel();
        header.setBackground(Theme.DANGER);
        header.setBorder(new EmptyBorder(14, 18, 14, 18));
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        JLabel headerLabel = new JLabel("Player " + playerNumber + " hit a landmine!");
        headerLabel.setFont(Theme.HEADING_FONT);
        headerLabel.setForeground(Color.WHITE);
        header.add(headerLabel);
        panel.add(header, BorderLayout.NORTH);

        JPanel body = new JPanel();
        body.setBackground(Theme.PANEL);
        body.setBorder(new EmptyBorder(18, 22, 18, 22));
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

        JTextArea promptArea = new JTextArea(question.prompt);
        promptArea.setFont(Theme.BODY_BOLD);
        promptArea.setForeground(Theme.TEXT_DARK);
        promptArea.setLineWrap(true);
        promptArea.setWrapStyleWord(true);
        promptArea.setOpaque(false);
        promptArea.setEditable(false);
        promptArea.setFocusable(false);
        promptArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        body.add(promptArea);
        body.add(Box.createRigidArea(new Dimension(0, 14)));

        char letter = 'A';
        for (int i = 0; i < question.choices.length; i++) {
            AnswerCard card = new AnswerCard(letter++, question.choices[i], i);
            cards.add(card);
            body.add(card);
            body.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        panel.add(body, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(Theme.PANEL);
        submitButton = new RoundedButton("Submit Answer", Theme.INDIGO);
        submitButton.setEnabled(false);
        submitButton.addActionListener(e -> submit());
        footer.add(submitButton);
        panel.add(footer, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel buildResultPanel(boolean wasCorrect) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(wasCorrect ? Theme.SUCCESS : Theme.DANGER);

        JLabel icon = new JLabel(wasCorrect ? "\u2713" : "\u2717", SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI", Font.BOLD, 72));
        icon.setForeground(Color.WHITE);
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel headline = new JLabel(wasCorrect ? "Correct!" : "Incorrect", SwingConstants.CENTER);
        headline.setFont(Theme.TITLE_FONT);
        headline.setForeground(Color.WHITE);
        headline.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel detail = new JLabel(
                wasCorrect ? "Move forward 3 cells" : ("Correct answer: " + correctAnswerText()),
                SwingConstants.CENTER);
        detail.setFont(Theme.BODY_FONT);
        detail.setForeground(Color.WHITE);
        detail.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(icon);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(headline);
        panel.add(Box.createRigidArea(new Dimension(0, 6)));
        panel.add(detail);
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    private String correctAnswerText() {
        char letter = (char) ('A' + question.correctIndex);
        return letter + ") " + question.choices[question.correctIndex];
    }

    private void submit() {
        if (selectedIndex < 0) return;
        correct = selectedIndex == question.correctIndex;
        cardLayout.show(stack, correct ? "correct" : "incorrect");
        Timer closeTimer = new Timer(1400, e -> dispose());
        closeTimer.setRepeats(false);
        closeTimer.start();
    }

    /** Blocks until the player answers; returns true if the answer was correct. */
    public boolean askAndWait() {
        setVisible(true); // modal: blocks here until dispose()
        return correct;
    }

    private void select(int index) {
        selectedIndex = index;
        for (AnswerCard card : cards) {
            card.setSelected(card.index == index);
        }
        submitButton.setEnabled(true);
    }

    /** One selectable answer choice, styled as a rounded card. */
    private class AnswerCard extends JPanel {
        final int index;
        private boolean selected = false;

        AnswerCard(char letter, String text, int index) {
            this.index = index;
            setOpaque(false);
            setLayout(new BorderLayout(10, 0));
            setBorder(new EmptyBorder(10, 14, 10, 14));
            setAlignmentX(Component.LEFT_ALIGNMENT);
            setMaximumSize(new Dimension(420, 42));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            JLabel badge = new JLabel(String.valueOf(letter));
            badge.setFont(Theme.BODY_BOLD);
            badge.setForeground(Theme.INDIGO);
            add(badge, BorderLayout.WEST);

            JLabel choiceLabel = new JLabel(text);
            choiceLabel.setFont(Theme.BODY_FONT);
            choiceLabel.setForeground(Theme.TEXT_DARK);
            add(choiceLabel, BorderLayout.CENTER);

            addMouseListener(new MouseAdapter() {
                @Override public void mouseClicked(MouseEvent e) { select(index); }
            });
        }

        void setSelected(boolean selected) {
            this.selected = selected;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            Theme.enableAA(g2d);
            g2d.setColor(selected ? new Color(0xE3, 0xE6, 0xFB) : Color.WHITE);
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
            g2d.setColor(selected ? Theme.INDIGO : new Color(0, 0, 0, 30));
            g2d.setStroke(new BasicStroke(selected ? 2f : 1f));
            g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 14, 14);
            g2d.dispose();
            super.paintComponent(g);
        }
    }
}
