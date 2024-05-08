package Vue;

import java.awt.*;

public class HexMetrics {
    public static final int HEX_WIDTH = 100; // Définir la largeur de l'hexagone
    public static final int HEX_HEIGHT = (int) (Math.sqrt(3) / 2 * HEX_WIDTH); // Hauteur calculée pour maintenir la proportion

    public static int viewOffsetX;
    public static int viewOffsetY;

    public static void updateViewPosition(int dx, int dy) {
        viewOffsetX += dx;
        viewOffsetY += dy;
    }

    public static Point calculateHexCenter(int x, int y) {
        int coordX = y * HEX_WIDTH/2 + x * HEX_WIDTH;
        int coordY = y * 3*HEX_HEIGHT/4;
        return new Point(coordX, coordY);
    }

    public static int getViewOffsetX() {
        return viewOffsetX;
    }

    public static int getViewOffsetY() {
        return viewOffsetY;
    }
}