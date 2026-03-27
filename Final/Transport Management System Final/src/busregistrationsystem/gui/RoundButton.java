package busregistrationsystem.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

public class RoundButton extends JButton {
    private int cornerRadius;
    private Color hoverColor;
    private boolean isHovered = false;
    private Point2D spreadOrigin;
    private float spreadProgress = 0f;
    private Timer spreadTimer;
    private final int SPREAD_DURATION = 1000; // milliseconds

    public RoundButton(String text, int cornerRadius) {
        super(text);
        this.cornerRadius = cornerRadius;
        this.hoverColor = getBackground().brighter();

        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);

        // Initialize animation timer
        spreadTimer = new Timer(50, e -> {
            if (isHovered && spreadProgress < 1f) {
                spreadProgress += 0.03f; // Adjust for speed
                if (spreadProgress > 1f) spreadProgress = 1f;
                repaint();
            } else if (!isHovered && spreadProgress > 0f) {
                spreadProgress -= 0.03f; // Fade out speed
                if (spreadProgress < 0f) spreadProgress = 0f;
                repaint();
            }
        });
        spreadTimer.start();

        // Mouse listeners
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                spreadOrigin = e.getPoint();
                spreadProgress = 0f; // Reset animation
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw base background
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));

        // Draw hover effect if active
        if (spreadProgress > 0 && spreadOrigin != null) {
            // Calculate current spread radius
            float maxRadius = (float) Math.max(getWidth(), getHeight()) * 1.5f;
            float currentRadius = spreadProgress * maxRadius;

            // Create gradient from center to edges
            float[] dist = {0.0f, 1.0f};
            Color[] colors = {
                    new Color(hoverColor.getRed(), hoverColor.getGreen(), hoverColor.getBlue(), 150),
                    new Color(hoverColor.getRed(), hoverColor.getGreen(), hoverColor.getBlue(), 0)
            };

            RadialGradientPaint paint = new RadialGradientPaint(
                    spreadOrigin,
                    currentRadius,
                    dist,
                    colors
            );

            g2.setPaint(paint);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));
        }

        // Draw text
        super.paintComponent(g2);
        g2.dispose();
    }

    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        this.hoverColor = bg.brighter();
    }
}