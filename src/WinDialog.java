import javax.swing.*;
import java.awt.*;

/** Result: true if the player chose "Play Again", false if they chose "Exit". */
public class WinDialog extends JDialog {
    private boolean playAgain = false;

    public WinDialog(Frame owner, String winnerName) {
        super(owner, "Quest Complete", true);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setSize(420, 320);
        setLocationRelativeTo(owner);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Theme.NAVY);
        panel.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        JLabel trophy = new JLabel("\u2691", SwingConstants.CENTER);
        trophy.setFont(new Font("Segoe UI", Font.BOLD, 64));
        trophy.setForeground(Theme.GOLD);
        trophy.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel headline = new JLabel(winnerName + " wins!", SwingConstants.CENTER);
        headline.setFont(Theme.TITLE_FONT);
        headline.setForeground(Theme.TEXT_LIGHT);
        headline.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = new JLabel("Quest complete - the minefield has been conquered.", SwingConstants.CENTER);
        sub.setFont(Theme.BODY_FONT);
        sub.setForeground(Theme.TEXT_MUTED);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 0));
        buttons.setOpaque(false);
        RoundedButton again = new RoundedButton("Play Again", Theme.GOLD);
        again.setForeground(Theme.TEXT_DARK);
        again.addActionListener(e -> { playAgain = true; dispose(); });
        RoundedButton exit = new RoundedButton("Exit", Theme.DANGER);
        exit.addActionListener(e -> { playAgain = false; dispose(); });
        buttons.add(again);
        buttons.add(exit);

        panel.add(Box.createVerticalGlue());
        panel.add(trophy);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(headline);
        panel.add(Box.createRigidArea(new Dimension(0, 6)));
        panel.add(sub);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(buttons);
        panel.add(Box.createVerticalGlue());

        setContentPane(panel);
    }

    public boolean showAndWait() {
        setVisible(true);
        return playAgain;
    }
}
