import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Central place for colors, fonts and small drawing helpers so every screen
 * in the game shares the same look instead of default Swing gray.
 * Palette is pulled from the LandMineQuest logo (navy / indigo / gold).
 */
public final class Theme {
    private Theme() {}

    // Core palette
    public static final Color NAVY_DARK   = new Color(0x11, 0x14, 0x2B);
    public static final Color NAVY        = new Color(0x1B, 0x1F, 0x3B);
    public static final Color INDIGO      = new Color(0x2E, 0x31, 0x92);
    public static final Color INDIGO_SOFT = new Color(0x3A, 0x3E, 0xA8);
    public static final Color GOLD        = new Color(0xF2, 0xB7, 0x05);
    public static final Color GOLD_SOFT   = new Color(0xFF, 0xD5, 0x66);
    public static final Color DANGER      = new Color(0xE6, 0x39, 0x46);
    public static final Color DANGER_SOFT = new Color(0xFF, 0x6B, 0x76);
    public static final Color SUCCESS     = new Color(0x3D, 0xCC, 0x8C);
    public static final Color PANEL       = new Color(0xF4, 0xF3, 0xEE);
    public static final Color TEXT_LIGHT  = new Color(0xF5, 0xF5, 0xF7);
    public static final Color TEXT_DARK   = new Color(0x20, 0x22, 0x30);
    public static final Color TEXT_MUTED  = new Color(0x8A, 0x8F, 0xB8);

    public static final Color PLAYER_ONE  = new Color(0xE6, 0x39, 0x46); // red
    public static final Color PLAYER_TWO  = new Color(0x38, 0xB6, 0xFF); // sky blue

    // Fonts
    public static final Font TITLE_FONT    = new Font("Segoe UI", Font.BOLD, 30);
    public static final Font HEADING_FONT  = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font BODY_FONT     = new Font("Segoe UI", Font.PLAIN, 15);
    public static final Font BODY_BOLD     = new Font("Segoe UI", Font.BOLD, 15);
    public static final Font BUTTON_FONT   = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font CELL_FONT     = new Font("Segoe UI", Font.PLAIN, 11);
    public static final Font BANNER_FONT   = new Font("Segoe UI", Font.BOLD, 18);

    public static GradientPaint backdrop(int w, int h) {
        return new GradientPaint(0, 0, NAVY_DARK, w, h, INDIGO);
    }

    public static void enableAA(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    }

    public static RoundRectangle2D roundRect(int x, int y, int w, int h, int arc) {
        return new RoundRectangle2D.Double(x, y, w, h, arc, arc);
    }

    /** Simple soft drop shadow for a rounded rect card. */
    public static void drawCardShadow(Graphics2D g2d, int x, int y, int w, int h, int arc) {
        Color shadow = new Color(0, 0, 0, 60);
        g2d.setColor(shadow);
        g2d.fill(roundRect(x + 4, y + 6, w, h, arc));
    }
}
