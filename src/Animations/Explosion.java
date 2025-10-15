package Animations;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

/**
 * A jagged lightning-style explosion with multiple bursts.
 */
public class Explosion {

    private int x, y;           // Center position
    private int radius;
    private int maxRadius;
    private boolean finished;
    private Color color;
    private Random rand;

    private int bursts = 3;     // number of separate lightning bursts

    public Explosion(int x, int y) {
        this.x = x;
        this.y = y;
        this.radius = 10;
        this.maxRadius = 60;
        this.finished = false;
        this.color = Color.WHITE;
        this.rand = new Random();
    }

    public void update() {
        if (radius < maxRadius) {
            radius += 3;
        } else {
            finished = true;
        }
    }

    public void draw(Graphics2D g) {
        if (finished) return;

        int alpha = Math.max(255 - radius * 4, 0);
        g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha));

        // Draw multiple lightning bursts
        for (int b = 0; b < bursts; b++) {
            int offsetX = rand.nextInt(20) + 5; // slightly offset each burst
            int offsetY = rand.nextInt(20) - 5;
            int centerX = x + offsetX;
            int centerY = y + offsetY;

            int spikes = 8;
            for (int i = 0; i < spikes; i++) {
                int endX = centerX + rand.nextInt(radius * 2) - radius;
                int endY = centerY + rand.nextInt(radius * 2) - radius;
                drawJaggedLine(g, centerX, centerY, endX, endY, 4);
            }
        }
    }

    private void drawJaggedLine(Graphics2D g, int x1, int y1, int x2, int y2, int segments) {
        int prevX = x1;
        int prevY = y1;

        for (int i = 1; i <= segments; i++) {
            float t = i / (float) segments;
            int nextX = (int) (x1 + t * (x2 - x1) + rand.nextInt(5) - 2);
            int nextY = (int) (y1 + t * (y2 - y1) + rand.nextInt(5) - 2);
            g.drawLine(prevX, prevY, nextX, nextY);
            prevX = nextX;
            prevY = nextY;
        }
    }

    public boolean isFinished() {
        return finished;
    }
}
